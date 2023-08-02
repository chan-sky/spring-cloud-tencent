/*
 * Tencent is pleased to support the open source community by making Spring Cloud Tencent available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.tencent.cloud.polaris.loadbalancer;

import java.util.Optional;

import com.tencent.polaris.api.config.consumer.LoadBalanceConfig;
import com.tencent.polaris.api.rpc.Criteria;
import com.tencent.polaris.router.api.core.RouterAPI;
import com.tencent.polaris.router.api.rpc.ProcessLoadBalanceRequest;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;

/**
 * PolarisRingHashLoadBalancer.
 *
 * @author sean yu
 * @author <a href="mailto:veteranchen@tencent.com">veteranchen</a>
 */
public class PolarisRingHashLoadBalancer extends AbstractPolarisLoadBalancer {

	public PolarisRingHashLoadBalancer(String serviceId,
			ObjectProvider<ServiceInstanceListSupplier> supplierObjectProvider,
			RouterAPI routerAPI) {
		super(serviceId, supplierObjectProvider, routerAPI);
	}

	@Override
	protected ProcessLoadBalanceRequest setProcessLoadBalanceRequest(ProcessLoadBalanceRequest req) {
		String hashKey = Optional.ofNullable(PolarisLoadBalancerRingHashKeyProvider.getHashKey()).orElse("");
		req.setLbPolicy(LoadBalanceConfig.LOAD_BALANCE_RING_HASH);
		Criteria criteria = new Criteria();
		criteria.setHashKey(hashKey);
		req.setCriteria(criteria);
		return req;
	}
}
