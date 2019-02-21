/**
 * Copyright © 2018 organization baomidou
 * <pre>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <pre/>
 */
package com.chen.pandora.db.starter.strategy;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 负载均衡策略
 * 一般默认为负载均衡策略，默认提供了一个随机策略
 * @author 陈添明
 * @date 2019/1/26
 */
public class LoadBalanceDynamicDataSourceStrategy implements DynamicDataSourceStrategy {

    /**
     * 负载均衡计数器
     */
    private AtomicInteger index = new AtomicInteger(0);

    @Override
    public String determineDataSource(List<String> dataSourceKeys) {
        return dataSourceKeys.get(Math.abs(index.getAndAdd(1) % dataSourceKeys.size()));
    }
}
