/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.carbon.apimgt.keymgt.revoke.service;

import org.apache.axis2.util.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.oltu.oauth2.common.OAuth;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.apimgt.keymgt.revoke.util.AccessTokenInfo;
import org.wso2.carbon.apimgt.keymgt.revoke.util.ApiKeyMgtRevokeUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides an API for revoking tokens by providing end user
 */
public class RevokeTokenService {

    private static final Log log = LogFactory.getLog(RevokeTokenService.class);

    /**
     * Service method to revoke all access tokens issued for given user under the given application
     * @param userName end user name
     * @param appName application name
     * @return if operation is success
     * @throws APIManagementException in case of revoke failure.
     */
    public boolean revokeTokens(String userName, String appName)
            throws APIManagementException {

        try {

            //find access tokens for user
            List<AccessTokenInfo> accessTokens = ApiKeyMgtRevokeUtil.getAccessTokenListForUser(userName,appName);
            //find revoke urls
            List<String> APIGatewayURLs = ApiKeyMgtRevokeUtil.getAPIGatewayURLs();
            List<String> APIRevokeURLs = new ArrayList<String>(APIGatewayURLs.size());

            for (String apiGatewayURL : APIGatewayURLs) {
                String [] apiGatewayURLs = apiGatewayURL.split(",");
                if(apiGatewayURL.length()> 1) {
                    //get https url
                    String apiHTTPSURL = apiGatewayURLs[1];
                    String revokeURL = apiHTTPSURL + "/" + "revoke";
                    APIRevokeURLs.add(revokeURL);
                }
            }

            //for each access token call revoke
            for (AccessTokenInfo accessToken : accessTokens) {
                for (String apiRevokeURL : APIRevokeURLs) {
                    revokeAccessToken(accessToken.getAccessToken(), accessToken.getConsumerKey(), accessToken
                            .getConsumerSecret(), apiRevokeURL);
                }
            }

            log.info("Successfully revoked all tokens issued for user=" + userName + "for application " + appName);
            return true;

        } catch (SQLException e) {
           throw new APIManagementException("Error while revoking token for user=" + userName + " app="+ appName, e);
        }

    }

    /**
     * Revoke the given access token. This call will reach gateway and clear token caches there as well
     * @param accessToken access token to revoke
     * @param consumerKey consumer key
     * @param consumerSecret consumer secret
     * @param revokeEndpoint revoke endpoint of the gateway
     * @throws APIManagementException
     */
    private void revokeAccessToken(String accessToken, String consumerKey, String consumerSecret, String
            revokeEndpoint) throws APIManagementException {
        try {
            if (accessToken != null) {
                URL revokeEndpointURL = new URL(revokeEndpoint);
                String revokeEndpointProtocol = revokeEndpointURL.getProtocol();
                int revokeEndpointPort = revokeEndpointURL.getPort();

                HttpClient revokeEPClient = APIUtil.getHttpClient(revokeEndpointPort, revokeEndpointProtocol);

                HttpPost httpRevokePost = new HttpPost(revokeEndpoint);

                // Request parameters.
                List<NameValuePair> revokeParams = new ArrayList<NameValuePair>(3);
                revokeParams.add(new BasicNameValuePair(OAuth.OAUTH_CLIENT_ID, consumerKey));
                revokeParams.add(new BasicNameValuePair(OAuth.OAUTH_CLIENT_SECRET, consumerSecret));
                revokeParams.add(new BasicNameValuePair("token", accessToken));


                //Revoke the Old Access Token
                httpRevokePost.setEntity(new UrlEncodedFormEntity(revokeParams, "UTF-8"));
                HttpResponse revokeResponse = revokeEPClient.execute(httpRevokePost);

                if (revokeResponse.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Token revoke failed : HTTP error code : " +
                            revokeResponse.getStatusLine().getStatusCode());
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Successfully submitted revoke request for user token " + accessToken+ ". HTTP " +
                                "status : 200");
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            handleException("Error while preparing request for token/revoke APIs", e);
        } catch (IOException e) {
            handleException("Error while creating tokens - " + e.getMessage(), e);
        }
    }

    /**
     * common method to throw exceptions.
     *
     * @param msg this parameter contain error message that we need to throw.
     * @param e   Exception object.
     * @throws org.wso2.carbon.apimgt.api.APIManagementException
     */
    private void handleException(String msg, Exception e) throws APIManagementException {
        log.error(msg, e);
        throw new APIManagementException(msg, e);
    }

}
