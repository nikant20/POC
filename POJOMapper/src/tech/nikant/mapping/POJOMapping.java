package tech.nikant.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import tech.nikant.annotations.Mapper;
import tech.nikant.pojo.EmployeeDto;

public class POJOMapping {

	public Object MapEmployeeDtoToEmployeeTO() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {

		// Seeting Values in POJO class
		EmployeeDto dto = new EmployeeDto(101, "Nikant", "Development");

		// Getting annotation from the Source Class
		Class dtoClass = EmployeeDto.class;
		Annotation annotation = dtoClass.getAnnotation(Mapper.class);
		Mapper myAnnotation = (Mapper) annotation;
		
		//Setting Taget Class name 
		Class<?> targetClass = myAnnotation.target();

		Field idField = dtoClass.getDeclaredField("id");
		idField.setAccessible(true);

		Field nameField = dtoClass.getDeclaredField("name");
		nameField.setAccessible(true);

		Field departmentField = dtoClass.getDeclaredField("department");
		departmentField.setAccessible(true);

		//Creating an object of Target Class & Setting values to respective setter fields
		Object targetClassObject = targetClass.newInstance();
		Method setNameMethod = targetClass.getMethod("setName", String.class);
		setNameMethod.invoke(targetClassObject, (String) nameField.get(dto));

		Method setDepartMentMethod = targetClass.getMethod("setDepartment", String.class);
		setDepartMentMethod.invoke(targetClassObject, (String) departmentField.get(dto));

		Method setidMethod = targetClass.getMethod("setId", Integer.class);
		setidMethod.invoke(targetClassObject, idField.getInt(dto));

		return targetClassObject;
	}
}
