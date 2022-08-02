package com.fsbr.models;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.fsbr.utils.DisplayAs;

public class DisplayableObjectTableModel<T> extends ObjectTableModel<T> {

	private static final long serialVersionUID = 604384461854849722L;
	
	private Map<Integer, ColumnInfo> columnInfoMap;

    public DisplayableObjectTableModel(Class<T> tClass) {
        init(tClass);
    }

    private void init(Class<T> tClass) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(tClass);
            this.columnInfoMap = new HashMap<>();
            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                Method m = pd.getReadMethod();
                DisplayAs displayAs = m.getAnnotation(DisplayAs.class);
                if (displayAs == null) {
                    continue;
                }
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.displayName = displayAs.value();
                columnInfo.index = displayAs.index();
                columnInfo.method = m;
                columnInfo.propertyName = pd.getName();
                columnInfoMap.put(columnInfo.index, columnInfo);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getValueAt(T t, int columnIndex) {
        try {
            return columnInfoMap.get(columnIndex)
                    .method.invoke(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getColumnCount() {
        return columnInfoMap.size();
    }

    @Override
    public String getColumnName(int column) {
        ColumnInfo columnInfo = columnInfoMap.get(column);
        if (columnInfo == null) {
            throw new RuntimeException("No column found for index " + column);
        }
        return columnInfo.displayName;
    }

    @Override
    public String getFieldName(int column) {
        ColumnInfo columnInfo = columnInfoMap.get(column);
        return columnInfo.propertyName;
    }

    public Class<?> getColumnClass(int columnIndex) {
        ColumnInfo columnInfo = columnInfoMap.get(columnIndex);
        return columnInfo.method.getReturnType();
    }

    private static class ColumnInfo {
        private Method method;
        private int index;
        private String displayName;
        public String propertyName;
    }
}