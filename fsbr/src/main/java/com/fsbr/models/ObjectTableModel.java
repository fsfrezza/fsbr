package com.fsbr.models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class ObjectTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = -515140858328665606L;
	
	private List<T> objectRows = new ArrayList<>();

    public List<T> getObjectRows() {
        return objectRows;
    }

    public void setObjectRows(List<T> objectRows) {
        this.objectRows = objectRows;
    }

    @Override
    public int getRowCount() {
        return objectRows.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T t = objectRows.get(rowIndex);
        return getValueAt(t, columnIndex);
    }

    public abstract Object getValueAt(T t, int columnIndex);

    @Override
    public abstract String getColumnName(int column);

    public abstract String getFieldName(int column);
}