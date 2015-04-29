package com.smartcommunity.cache;

import java.util.HashSet;
import java.util.Set;

import com.smartcommunity.mapper.ResourcesMapper;
import com.smartcommunity.pojo.Resources;

public class ResourceCache {

	public static java.util.Map<Integer,Set<String>> resourceMap = new java.util.HashMap<Integer, java.util.Set<String>>();
	public static boolean isPermit(java.util.Set<Integer> roles,String url) {
		if (resourceMap == null) {
			resourceMap = new java.util.HashMap<Integer, java.util.Set<String>>();
		}
		for (Integer roleid : roles) {
			Set<String> set = resourceMap.get(roleid);
			if (set == null) {
				getResourceMap(roleid);
			}			
			if (  set.contains(url) )
					return true;
			int index = url.indexOf(".action");
			if (index != -1 && set.contains(url.substring(0,index))) {
					return true;
				}
		}
		return false;
	}

	/**
	 * 取得角色与资源的对应关系
	 */
	public synchronized  static void getResourceMap(Integer roleid) {
		if (resourceMap.get(roleid) != null) {
			return ;
		}
		org.springframework.context.ApplicationContext applicationContext = com.smartcommunity.listener.ApplicationListener.getApplicationContext();
		ResourcesMapper resourcesMapper = (ResourcesMapper) applicationContext.getBean("resourcesMapper");
		java.util.List<com.smartcommunity.pojo.Resources> resources = resourcesMapper.selectByRoleId(roleid);

		java.util.Set<String> urlSet = new HashSet<String>();
		for (Resources resource : resources) {

			urlSet.add(resource.getUrl());
		}
		resourceMap.put(roleid, urlSet);
		System.out.println("cache" + resourceMap.get(roleid));
	}
}
