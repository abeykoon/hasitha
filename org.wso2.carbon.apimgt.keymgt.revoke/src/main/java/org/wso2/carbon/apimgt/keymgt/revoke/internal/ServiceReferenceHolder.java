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

package org.wso2.carbon.apimgt.keymgt.revoke.internal;

import org.wso2.carbon.apimgt.impl.APIManagerConfigurationService;

/**
 * Singleton class holding service references
 */
public class ServiceReferenceHolder {

    private static final ServiceReferenceHolder instance = new ServiceReferenceHolder();

    private APIManagerConfigurationService amConfigurationService;

    private ServiceReferenceHolder() {

    }

    /**
     * Get ServiceReferenceHolder instance
     * @return singleton object
     */
    public static ServiceReferenceHolder getInstance() {
        return instance;
    }

    /**
     * Get API Manager configuration service. This deals with all configuration related actions
     * on API Manager
     * @return APIManagerConfigurationService instance
     */
    public APIManagerConfigurationService getAPIManagerConfigurationService() {
        return amConfigurationService;
    }

    /**
     * Set API Manager configuration service. This deals with all configuration related actions
     * on API Manager
     * @param amConfigurationService instance to set
     */
    public void setAPIManagerConfigurationService(APIManagerConfigurationService amConfigurationService) {
        this.amConfigurationService = amConfigurationService;
    }

}
