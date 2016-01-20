package com.ai.wocloud.system.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateUtil {
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 获取当前日期和时间的中文显示
	 * 
	 * @return
	 */
	public static String nowCn() {
		return new SimpleDateFormat("yyyy年MM月dd日 HH时:mm分:ss秒").format(new Date());
	}

	/**
	 * 获取当前日期和时间的英文显示
	 * 
	 * @return
	 */
	public static String nowEn() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 获取当前日期的中文显示
	 * 
	 * @return
	 */
	public static String nowDateCn() {
		return new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
	}

	/**
	 * 获取当前日期的英文显示
	 * 
	 * @return
	 */
	public static String nowDateEn() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 获取当前时间的中文显示
	 * 
	 * @return
	 */
	public static String nowTimeCn() {
		return new SimpleDateFormat("HH时:mm分:ss秒").format(new Date());
	}

	/**
	 * 获取当前时间的英文显示
	 * 
	 * @return
	 */
	public static String nowTimeEn() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	/**
	 * 获取当前时间的英文显示
	 * 
	 * @return
	 */
	public static String getNowDate() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	/**
	 * 得到本日的前几个月时间 如果number=2当日为2007-9-1 00:00:00,那么获得2007-7-1 00:00:00
	 * 
	 * @param number
	 * @return
	 */
	public static String getDateBeforeMonth(int number) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -number);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static String getCurrYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currYearFirst);
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static String getCurrYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currYearLast);
	}

	/**
	 * 获取当前毫秒数
	 */
	public static long getNowDateTime() {
		return (new Date()).getTime();
	}

	/**
	 * 获取当前时间毫秒
	 */
	public static String getNowDateshm() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * 
	 */
	public static String getNowDates() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * 
	 * @param dates
	 * @param formata
	 * @param formatb
	 * @return
	 */
	public static String toFormat(String dates, String formata, String formatb) {
		DateFormat format1 = new SimpleDateFormat(formata);
		DateFormat format2 = new SimpleDateFormat(formatb);
		Date date = null;
		String str2 = null;
		try {
			date = format1.parse(dates);
			str2 = format2.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str2;
	}

	/**
	 * 获取某年某月第一天日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月份
	 * @param format
	 *            显示格式
	 * @return
	 * @author liangmeng
	 */
	public static String getCurrMonthFirst(int year, int month, String format) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return new SimpleDateFormat(format).format(cal.getTime());
	}

	/**
	 * 获取某年某月最后一天日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月份
	 * @param format
	 *            显示格式
	 * @return
	 * @author liangmeng
	 */
	public static String getCurrMonthLast(int year, int month, String format) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return new SimpleDateFormat(format).format(cal.getTime());
	}

	/**
	 * 获取当前月向前X个月的list
	 * 
	 * @param count
	 *            向前推的月份数量
	 * @return
	 * @author liangmeng
	 */
	public static List<Map<String, Object>> getPerMonth(int count) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < count; i++) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -i);
			Map<String, Object> map = new HashMap<String, Object>();
			int year = cal.get(Calendar.YEAR);
			int month = (cal.get(Calendar.MONTH) + 1);
			map.put("formatStr", year + "年" + month + "月");
			map.put("year", year);
			map.put("month", month);
			map.put("yyyyMM", String.valueOf(year) + (month < 10 ? "0" + month : month));
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取从上个月开始  长度为count的 list
	 * 
	 * @param count
	 *            向前推的月份数量
	 * @return
	 * @author yangzh
	 */
	public static List<Map<String, Object>> getPerMonth1(int count) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < count; i++) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -i);
			Map<String, Object> map = new HashMap<String, Object>();
			int year = cal.get(Calendar.YEAR);
			int month = (cal.get(Calendar.MONTH));
			map.put("formatStr", year + "年" + month + "月");
			map.put("year", year);
			map.put("month", month);
			map.put("yyyyMM", String.valueOf(year) + (month < 10 ? "0" + month : month));
			list.add(map);
		}
		return list;
	}

	/**
     * 获取当前月向前X个月的list
     * @param count 向前推的月份数量
     * @return 
     * @author liangmeng
     */
    public static List<Map<String,Object>> getMonthFromPerMonth(int count){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(int i=1;i<count+1;i++){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -i);
            Map<String,Object> map = new HashMap<String, Object>();
            int year = cal.get(Calendar.YEAR);
            int month = (cal.get(Calendar.MONTH)+1);
            map.put("formatStr",  year+ "年" + month+"月");
            map.put("year", year);
            map.put("month", month);
            map.put("yyyyMM", String.valueOf(year) + (month<10? "0"+month : month));
            list.add(map);
        }
        return list;
    }
	/**
     * 获取当前年份及向前年份的年份list
     * @param count 向前推的年份数量
     * @return 
     */
    public static List<Map<String,Object>> getYearsFromCurrentYears(int count){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(int i=0;i<count;i++){
            Calendar cal = Calendar.getInstance();
            Map<String,Object> map = new HashMap<String, Object>();
            int year = cal.get(Calendar.YEAR)-i;
            map.put("formatStr",  year+ "年");
            map.put("year", year);
            list.add(map);
        }
        return list;
    }
	/**
	 * 转换为固定格式字符串
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            格式
	 * @return
	 * @author moubd
	 */
	public static String toFormat(Timestamp date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 转换为指定格式字符串
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            格式
	 * @return
	 */
	public static String toFormat(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
	/**
	 * 传入日期的下月下月的第一天
	 */
	@SuppressWarnings("deprecation")
    public static String nextMonthFirtDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1900+date.getYear());
		calendar.set(Calendar.MONTH, date.getMonth()+1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return simpleDateFormat.format(calendar.getTime());
	}
	/**
	 * 传入日期的最后一天
	 */
	@SuppressWarnings("deprecation")
    public static String thisMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1900+date.getYear());
		calendar.set(Calendar.MONTH, date.getMonth()+1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return simpleDateFormat.format(calendar.getTime());
	}
	/**
	 * 传入日期是否为当前月的最后一天
	 */
	public static boolean isThisMonthLastDay(Date date) {
		return simpleDateFormat.format(date.getTime()).equals(thisMonthLastDay(new Date()));
	}
	/**
	 * 传入日期是否为下月的第一天
	 */
	public static boolean isNextMonthFirstDay(Date date) {
		return simpleDateFormat.format(date.getTime()).equals(nextMonthFirtDay(new Date()));
	}
	public static void main(String[] args) throws Exception {
		//System.out.println(thisMonthLastDay(simpleDateFormat.parse("2014-4-1")));
		System.out.println(isNextMonthFirstDay(simpleDateFormat.parse("2014-5-1")));
	}
	/**
     * 获取近期几个月的list集合
     * @param number  要获取的月个数   formart 指定格式 默认:"yyyy年M月"
     * @return
     * @author 李明顶
     *
     * Date: 2014年4月2日 <br>
     */
    public static List<String> getLatelyMonthForNum(final int number,final String ...formart ){
        String formatTem = "yyyy年MM月";
        if(formart!=null&&formart.length==1){
            formatTem = formart[0];
        }
        List<String>  list = new ArrayList<String>();
        int currenMouth = Calendar.getInstance().get(Calendar.MONTH);//当前月-1
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat(formatTem);
        for(int i=0;i<number;i++){ 
            Calendar c=Calendar.getInstance();
            c.add(Calendar.MONTH, -1);//今天的时间月份-1支持1月的上月
            c.set(Calendar.MONTH,currenMouth-i);
            list.add(simpleDateFormat.format(c.getTime()).toString());
        }
        return list;
    }
    /**
     * 根据查询月获取查询当月的起止日期      可以指定月参数格式    和    起止日期格式
     * @param date   月参数格式默认:yyyy年M月     formart:起止日期格式默认:yyyy年M月dd日
     * @return 查询当月的起止日期字符串
     * @author 李明顶
     *
     * Date: 2014年4月3日 <br>
     */
    @SuppressWarnings("deprecation")
    public static String getStartAndEndDate(final String date,final String ... formart){
        String dateFormat = "yyyy年MM月";//默认格式
        String startAndEndDateFormart = "yyyy年MM月dd日";//默认起止日期格式
        if(formart!=null&&formart.length==1){
            dateFormat = formart[0];
        }
        if(formart!=null&&formart.length==2){
            startAndEndDateFormart = formart[1];
        }
        
        //是否是当月
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String nowDate = simpleDateFormat.format(new Date()).toString();
        if(nowDate.equals(date)){
           String startDate = nowDate+"01日";
           String endDate = new SimpleDateFormat(startAndEndDateFormart).format(new Date()).toString();//默认当前月
            return startDate+"至"+endDate;       
        } 
        //起始日期
        String startDate = date+"01日";  
        //截止日期 
        Date endDateD = null;
       try {
           endDateD = simpleDateFormat.parse(date);
       } catch (ParseException e) {
       } 
        int m = endDateD.getMonth();  
        int y = endDateD.getYear();  
        Date firstDay = new Date(y,m+1,1) ;  
        int min = 24*60*60*1000;    
        Date end = new Date(firstDay.getTime()-min);
        String endDate = new SimpleDateFormat(startAndEndDateFormart).format(end);
        return startDate+"至"+endDate;
    }
    
    //获取指定月份的最后一天
    public static Date lastDayOfMonth(Date date)  {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        String dateStr = simpleDateFormat.format(calendar.getTime());
        dateStr = dateStr+" 23:59:59";
        SimpleDateFormat mat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return mat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //获取指定月份的下个月的第一天
    public static Date firstDayOfNextMonth(Date date){
        // 获取Calendar   
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);
        
        // 设置Calendar月份数为下一个月
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        // 设置Calendar日期为下一个月一号 
        calendar.set(Calendar.DATE, 1);
        
        String dateStr = simpleDateFormat.format(calendar.getTime());
        dateStr = dateStr+" 00:00:00";
        
        SimpleDateFormat mat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return mat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Date getProductFinalTime(){
        String dateStr = "2099-12-31 23:59:59";
        SimpleDateFormat mat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return mat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String timstamp2String(Timestamp timestamp){
        if(timestamp==null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒      
        String str = df.format(timestamp);
        if(str!=null && str.length()>10)
            str = str.substring(0, 10);
        return str;
    }
 	
 	public static String timstamp3String(Timestamp timestamp){
        if(timestamp==null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒      
        String str = df.format(timestamp);
        if(str!=null && str.length()>10)
            str = str.substring(0, 10);
        return str;
    }
}

