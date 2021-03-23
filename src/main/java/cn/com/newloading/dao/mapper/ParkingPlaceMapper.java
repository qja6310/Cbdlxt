package cn.com.newloading.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import cn.com.newloading.bean.ParkingPlace;
import cn.com.newloading.dao.provider.ParkingPlaceProvider;

@Mapper
public interface ParkingPlaceMapper {

	@SelectProvider(type = ParkingPlaceProvider.class,method = "queryParkPlaceInfo" )
	public List<ParkingPlace> queryParkPlaceInfo(ParkingPlace parkingPlace);
	
	@SelectProvider(type = ParkingPlaceProvider.class,method = "queryInfoCountByParams" )
	public int queryInfoCountByParams(ParkingPlace parkingPlace);
	
	@Select("SELECT DISTINCT area FROM t_parking_place_info")
	List<String> queryAllArea();
	
	@Update("UPDATE t_parking_place_info SET `status` = #{status} WHERE id = #{id}")
	int editStatus(ParkingPlace parkingPlace);
	
	@Select("SELECT COUNT(*) count,ppi.`status` FROM t_parking_place_info ppi GROUP BY ppi.`status` ORDER BY ppi.`status`")
	List<Map<String, String>> count();
	
	@Insert("INSERT INTO `parking`.`t_parking_place_info`(`area`, `number`, `status`) VALUES (#{area}, #{number}, '1')")
	int add(ParkingPlace parkingPlace);
	
	@Select("SELECT COUNT(*) FROM `t_parking_place_info` WHERE area = #{area} AND number = #{number}")
	int check(ParkingPlace parkingPlace);
}
