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
 * Class holding DB related constants
 */
public class RDBMSConstants {

    protected static final String PS_SELECT_ACCESS_TOKENS = "SELECT AKM.CONSUMER_KEY, CON_APP.CONSUMER_SECRET, TOKEN.ACCESS_TOKEN " +
            "FROM " +
            "IDN_OAUTH_CONSUMER_APPS CON_APP, AM_APPLICATION APP, IDN_OAUTH2_ACCESS_TOKEN  TOKEN, AM_APPLICATION_KEY_MAPPING AKM " +
            "WHERE TOKEN.AUTHZ_USER =? " +
            "AND APP.NAME=? " +
            "AND TOKEN.TOKEN_STATE = 'ACTIVE' " +
            "AND TOKEN.CONSUMER_KEY_ID = CON_APP.ID " +
            "AND CON_APP.CONSUMER_KEY=AKM.CONSUMER_KEY " +
            "AND AKM.APPLICATION_ID = APP.APPLICATION_ID";

}
