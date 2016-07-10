/*
*Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.apimgt.keymgt.revoke.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.impl.APIManagerConfiguration;
import org.wso2.carbon.apimgt.impl.dto.Environment;
import org.wso2.carbon.apimgt.impl.utils.APIMgtDBUtil;
import org.wso2.carbon.apimgt.keymgt.revoke.internal.ServiceReferenceHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class for the component.
 */
public class ApiKeyMgtRevokeUtil {

    private static final Log log = LogFactory.getLog(ApiKeyMgtRevokeUtil.class);

    /**
     * Get API gateway URLs defined on apiManager.xml
     * @return list of gateway urls
     */
    public static List<String> getAPIGatewayURLs() {
        APIManagerConfiguration apiConfig = ServiceReferenceHolder.getInstance().getAPIManagerConfigurationService()
                .getAPIManagerConfiguration();
        Map<String, Environment> APIEnvironments = apiConfig.getApiGatewayEnvironments();
        List<String> gatewayURLs = new ArrayList<String>(2);
        for (Environment environment : APIEnvironments.values()) {
            gatewayURLs.add(environment.getApiGatewayEndpoint());
        }
        return gatewayURLs;
    }


    /**
     * Get a list of access tokens issued for given user under the given app. Returned object carries consumer key
     * and secret information related to the access token
     *
     * @param userName end user name
     * @param appName application name
     * @return list of tokens
     * @throws SQLException in case of a DB issue
     */
    public static List<AccessTokenInfo> getAccessTokenListForUser(String userName, String appName) throws SQLException {

        List<AccessTokenInfo> accessTokens = new ArrayList<AccessTokenInfo>(5);
        Connection connection = getDBConnection();
        PreparedStatement consumerSecretIDPS = connection.prepareStatement(RDBMSConstants.PS_SELECT_ACCESS_TOKENS);
        consumerSecretIDPS.setString(1, userName);
        consumerSecretIDPS.setString(2, appName);

        ResultSet consumerSecretIDResult = consumerSecretIDPS.executeQuery();

        while (consumerSecretIDResult.next()) {
            String consumerKey = consumerSecretIDResult.getString(1);
            String consumerSecret = consumerSecretIDResult.getString(2);
            String accessToken = consumerSecretIDResult.getString(3);

            accessTokens.add(new AccessTokenInfo(consumerKey,consumerSecret,accessToken));
        }

        return accessTokens;
    }


    /**
     * Get connection to APIM DB
     *
     * @return a connection
     * @throws SQLException in case of connection issue
     */
    private static Connection getDBConnection() throws SQLException {
        return APIMgtDBUtil.getConnection();
    }


}
