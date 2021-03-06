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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.apimgt.impl.APIManagerConfigurationService;

/**
 * @scr.component name="api.keymgt.revoke.component" immediate="true"
 * @scr.reference name="api.manager.config.service"
 * interface="org.wso2.carbon.apimgt.impl.APIManagerConfigurationService" cardinality="1..1"
 * policy="dynamic" bind="setAPIManagerConfigurationService" unbind="unsetAPIManagerConfigurationService"
 */
public class APIKeyRevokeServiceComponent {

    private static Log log = LogFactory.getLog(APIKeyRevokeServiceComponent.class);

    protected void activate(ComponentContext ctxt) {
        log.info("initialized key management revoke service");
    }

    /**
     * Set configuration service
     * @param configurationService service to set
     */
    public void setAPIManagerConfigurationService(APIManagerConfigurationService configurationService) {
        ServiceReferenceHolder.getInstance().setAPIManagerConfigurationService(configurationService);
    }

    /**
     * Unset configuration service
     * @param configurationService service to unset
     */
    public void unsetAPIManagerConfigurationService(APIManagerConfigurationService configurationService) {
        ServiceReferenceHolder.getInstance().setAPIManagerConfigurationService(null);
    }
}
