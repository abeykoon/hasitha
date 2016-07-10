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

/**
 * This class represents the Object having access token together with other information
 * needed to revoke it.
 */
public class AccessTokenInfo {

    private String accessToken;
    private String consumerKey;
    private String consumerSecret;

    /**
     * Constructor for AccessTokenInfo.
     * @param consumerKey consumer key for the access token
     * @param consumerSecret consumer secret for the access token
     * @param accessToken access token
     */
    public AccessTokenInfo(String consumerKey, String consumerSecret, String accessToken) {
        this.accessToken = accessToken;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    /**
     * Getter for access token
     * @return access token
     */
    public String getAccessToken() {
        return accessToken;
    }


    /**
     * Getter for consumer key for the respective access token
     * @return consumer key
     */
    public String getConsumerKey() {
        return consumerKey;
    }


    /**
     * Getter for consumer secret for the respective access token
     * @return consumer secret
     */
    public String getConsumerSecret() {
        return consumerSecret;
    }

}
