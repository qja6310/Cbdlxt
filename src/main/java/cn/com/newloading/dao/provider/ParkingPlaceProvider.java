package cn.com.newloading.dao.provider;


import org.apache.ibatis.jdbc.SQL;

import cn.com.newloading.bean.ParkingPlace;
import cn.com.newloading.utils.StringUtil;

public class ParkingPlaceProvider extends BaseProvider{
	
	public String queryParkPlaceInfo(ParkingPlace parkingPlace){
		SQL sql = new SQL();
		sql.SELECT("ppi.*,c.param statusName").FROM(PPI +" ppi INNER JOIN "+C+" c ON ppi.`status` = c.`code`").WHERE("c.type = 'pps'");
		if (StringUtil.isNotBlank(parkingPlace.getArea())) {
			sql.WHERE("ppi.area = #{area}");
		}
		if (StringUtil.isNotBlank(parkingPlace.getNumber())) {
			sql.WHERE("ppi.number = #{number}");
		}
		if (StringUtil.isNotBlank(parkingPlace.getStatus())) {
			sql.WHERE("ppi.status = #{status}");
		}
		String desc = "ppi.id";
		if(parkingPlace.getStart() != null && parkingPlace.getLimit() != null) {
			desc += " limit #{start},#{limit}";
		}
		sql.ORDER_BY(desc);
		return sql.toString();
	}
	
	public String queryInfoCountByParams(ParkingPlace parkingPlace){
		SQL sql = new SQL();
		sql.SELECT("COUNT(*)").FROM(PPI);
		if (StringUtil.isNotBlank(parkingPlace.getArea())) {
			sql.WHERE("area = #{area}");
		}
		if (StringUtil.isNotBlank(parkingPlace.getNumber())) {
			sql.WHERE("number = #{number}");
		}
		if (StringUtil.isNotBlank(parkingPlace.getStatus())) {
			sql.WHERE("status = #{status}");
		}
		return sql.toString();
	}
	
}
