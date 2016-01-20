package com.ai.wocloud.system.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.ai.wocloud.action.LoginInfoAction;
import com.ai.wocloud.bean.OsTemplate;

public class JsonToObj {

	private static Logger logger = Logger.getLogger(LoginInfoAction.class);

	public static List<OsTemplate> jsonUtil(String template, Object obj) {
		JSONArray jsonArr = JSONArray.fromObject(template);

		List<OsTemplate> resultList = null;

		if (obj instanceof OsTemplate) {
			resultList = new ArrayList<OsTemplate>();
			if (jsonArr.size() < 1) {
				OsTemplate osTemplate = new OsTemplate();
				osTemplate.setSystem("暂无数据");
				osTemplate.setId("暂无数据");
				osTemplate.setName("暂无数据");
				osTemplate.setOstypeid("暂无数据");
				osTemplate.setOstypename("暂无数据");
			} else {

				for (int i = 0; i < jsonArr.size(); i++) {
					OsTemplate osTemplate = new OsTemplate();
					if (jsonArr.getJSONObject(i).containsKey("system")) {

						osTemplate.setSystem(jsonArr.getJSONObject(i)
								.getString("system"));
					} else {
						logger.error("system的值为空");
					}

					if (jsonArr.getJSONObject(i).containsKey("id")) {

						osTemplate.setId(jsonArr.getJSONObject(i).getString(
								"id"));
					} else {
						logger.error("id的值为空");
					}

					if (jsonArr.getJSONObject(i).containsKey("name")) {

						osTemplate.setName(jsonArr.getJSONObject(i).getString(
								"name"));
					} else {
						logger.error("name的值为空");
					}

					if (jsonArr.getJSONObject(i).containsKey("ostypeid")) {

						osTemplate.setOstypeid(jsonArr.getJSONObject(i)
								.getString("ostypeid"));
					} else {
						logger.error("ostypeid的值为空");
					}

					if (jsonArr.getJSONObject(i).containsKey("ostypename")) {

						osTemplate.setOstypename(jsonArr.getJSONObject(i)
								.getString("ostypename"));
					} else {
						logger.error("ostypename的值为空");
					}

					resultList.add(osTemplate);
				}
				Set<OsTemplate> ts = new HashSet<OsTemplate>();
				ts.addAll(resultList);

			}
		}
		return resultList;

	}

}
