package com.kakaopaysec.rrss.core.util;

import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;

@Component
public class CommonUtil {

	public static String[] getNullPropertyNames(Object source) {
		String[] basic = new String[]{};
		Set<String> emptyNames = new HashSet<>();
		
		if(source != null) {
			final BeanWrapper src = new BeanWrapperImpl(source);
			PropertyDescriptor[] pds = src.getPropertyDescriptors();
			
			for(PropertyDescriptor pd : pds) {
				Object srcValue = src.getPropertyValue(pd.getName());
				if(srcValue == null) {
					emptyNames.add(pd.getName());
				}
			}
		}
		
		return Stream.concat(Arrays.stream(basic), Arrays.stream(emptyNames.toArray(new String[0]))).toArray(String[]::new);
	}
	
	@SuppressWarnings("unchecked")
	public static OrderSpecifier<?>[] getOrderSpecifiers(Sort sorts, Class<?> clazz) {
		String className = clazz.getSimpleName();
		final String orderVariable = String.valueOf(Character.toLowerCase(className.charAt(0)))
				.concat(className.substring(1));
		return sorts.stream().map(order -> new OrderSpecifier(Order.valueOf(order.getDirection().toString()),
				new PathBuilder(clazz, orderVariable).get(order.getProperty()))).toArray(OrderSpecifier[]::new);
	}
	
	/**
	 * 날짜비교
	 *
	 * @param dateFormat (일자, 일시 등)
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author : 정민
	 * @date : 2021.06.01
	 *
	 * 1 startDate > endDate
	 * 0 startDate == endDate
	 * -1 startDate < endDate
	 *
	 */
	public static int dateCompareTo(String dateFormat, String startDate, String endDate) {
	    SimpleDateFormat format = new SimpleDateFormat(dateFormat);
	    Date today = null;
	    try {
	        today = format.parse(startDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    Date end = null;
	    try {
	        end = format.parse(endDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    int result = today.compareTo(end);
	    return result;
	}


}
