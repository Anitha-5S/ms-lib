package com.c2.lc.lib.utils;


import com.c2.lc.lib.exceptions.DataFormatException;
import org.springframework.stereotype.Component;

@Component
public class DataParser {

    public Long validateId(String id) throws DataFormatException {
        Long lId;
        try {
            lId = Long.valueOf(id);
        } catch (Exception e) {
            throw new DataFormatException(id);
        }

        return lId;
    }


	public Double getDoubleValue(String val) throws DataFormatException {
		Double lId;
		try {
			lId = Double.valueOf(val);
		} catch (Exception e) {
			throw new DataFormatException(val);
		}
		return lId;
	}

	public Double getNotNullDoubleValue(String val) throws DataFormatException {
        Double lId = 0.0;
        try {
            if (val != null && val.trim().length() > 0) lId = Double.valueOf(val);
        } catch (Exception e) {
            throw new DataFormatException(val);
        }
        return lId;
    }

    public Double getNullableDoubleValue(String val) throws DataFormatException {
        Double lId = null;
        try {
            if (val != null && val.trim().length() > 0) lId = Double.valueOf(val);
        } catch (Exception e) {
            throw new DataFormatException(val);
        }
        return lId;
    }

	public Integer getNullableIntegerValue(String val) throws DataFormatException {
		Integer lId = null;
		try {
			if (val != null && val.trim().length() > 0) lId = Integer.valueOf((val));
		} catch (Exception e) {
			throw new DataFormatException(val);
		}
		return lId;
	}

    public Double getNotNullDoubleValue(Double val){
		return val == null ? 0.0 : val;
	}

	public Double getRoundValue(String val) throws DataFormatException {
		Double lId = 0.0;
		try {
			if (val != null && val.trim().length() > 0) lId = Double.valueOf(Math.round(Double.valueOf(val)));
		} catch (Exception e) {
			throw new DataFormatException(val);
		}
		return lId;
	}

	public Double getRoundValue(Double value){
		return value == null ? 0.0 : Math.round(Double.valueOf(value));
	}

	public Double getRoundValue2(Double value){
		return value == null ? 0.0 : Math.round(Double.valueOf(value) * 100.0) / 100.0; // return .0 so that division result is decimal
	}

	public Long getNullableLongValue(String val) throws DataFormatException {
        Long lId = null;
        try {
            if (val != null && val.trim().length() > 0) lId = Long.valueOf(val);
        } catch (Exception e) {
            throw new DataFormatException(val);
        }

        return lId;
    }

    public Integer getNotNullIntegerValue(String val) throws DataFormatException {
        Integer lId = 0;
        try {
            if (val != null && val.trim().length() > 0) lId = Integer.valueOf(val);
        } catch (Exception e) {
            throw new DataFormatException(val);
        }

        return lId;
    }

	public Integer getNotNullIntegerValue(Integer val) {
		return val == null ? 0 : val;
	}

    public Short getShortValue(String val) throws DataFormatException {
        Short lId;
        try {
            lId = Short.valueOf(val);
        } catch (Exception e) {
            throw new DataFormatException(val);
        }

        return lId;
    }

    public Long getLongValue(String val) throws DataFormatException {
        Long lId;
        try {
            lId = Long.valueOf(val);
        } catch (Exception e) {
            throw new DataFormatException(val);
        }

        return lId;
    }

    public Integer getIntegerValue(String val) throws DataFormatException {
        Integer lId;
        try {
            lId = Integer.valueOf(val);
        } catch (Exception e) {
            throw new DataFormatException(val);
        }

        return lId;
    }

    public String getObjectStringValue(Object obj){
        String objString = Constants.EMPTY_STRING;
        if (obj != null){
            objString = obj.toString();
        }
        return objString;
    }

	public String getObjectNullableStringValue(Object obj){
		return obj == null ? null :obj.toString();
	}

	//TODO - Replace below with getTrimmedStringValue(String)
    public String getStringValue(String obj){
        String objString = Constants.EMPTY_STRING;
        if (obj != null){
            objString = obj;
        }
        return objString;
    }

    public String getTrimmedStringValue(String obj){
        return getTrimmedStringValue(obj, Constants.EMPTY_STRING);
    }

    public String getTrimmedStringValue(String obj, String defaultStr){
        String objString = defaultStr;
        if (obj != null){
            objString = obj.trim();
        }
        return objString;
    }

    public String getNotNullableTrimmedUpperCase(String obj) throws DataFormatException {

        String objString;
        try {
            objString = obj.trim().toUpperCase();
        } catch (Exception e) {
            throw new DataFormatException(obj);
        }
        return objString;
    }
    public String getDoubleStringValue(Double obj){
        String objString = "0.000";
        if (obj != null){
            objString = String.format( "%.3f", obj );
        }
        return objString;
    }

    public String getDouble2StringValue(Double obj) {
        String objString = "0.00";
        if (obj != null) {
            objString = String.format("%.2f", obj);
        }
        return objString;
    }

    public String getDouble1StringValue(Double obj) {
        String objString = "0.0";
        if (obj != null) {
            objString = String.format("%.1f", obj);
        }
        return objString;
    }

    public String getIntegerStringValue(Integer obj){
        String objString = "0";
        if (obj != null){
            objString = obj.toString();
        }
        return objString;
    }

	public String getNullableIntegerStringValue(Integer obj){
		String objString = null;
		if (obj != null){
			objString = obj.toString();
		}
		return objString;
	}

	public String getShortStringValue(Short obj){
        String objString = "0";
        if (obj != null){
            objString = obj.toString();
        }
        return objString;
    }

    public String getLongStringValue(Long obj){
        String objString = "0";
        if (obj != null){
            objString = obj.toString();
        }
        return objString;
    }

	public String getNullableLongStringValue(Long obj){
		String objString = null;
		if (obj != null){
			objString = obj.toString();
		}
		return objString;
	}

	public Long getNullableId(String id) throws DataFormatException {
		Long lId = null;
		try {
			if (id != null) {lId = Long.valueOf(id);}
		} catch (Exception e) {
			
			throw new DataFormatException(id);
		}
		return lId;
	}
    
    
	public String getMaskedPhoneNumberString(String message, String replace, String number, int last) {
		StringBuilder ret = new StringBuilder();
		int length = number.length();
		for (int i = 1; i <= length - last; i++) {
			ret.append("*");
		}
		ret.append(number.substring(length - last));
		return message.replace(replace, ret.toString());
	}
	

//    public Double getNullableDoubleValue(String val) throws DataFormatException {
//        Double lId = null;
//        try {
//            if (val != null && val.trim().length() > 0) lId = Double.valueOf(val);
//        } catch (Exception e) {
//            throw new DataFormatException(val);
//        }
//        return lId;
//    }
//
//    public Double getNullableDoubleValue(Double val) {
//         return val == null ? 0.0 : val;
//    }

    public Short getNullableShortValue(String val) throws DataFormatException {
        Short lId = 0;
        try {
            if (val != null && val.trim().length() > 0) lId = Short.valueOf(val);
        } catch (Exception e) {
            throw new DataFormatException(val);
        }

        return lId;
    }

    public Short getNullableShortValue(Short val) throws DataFormatException {
        return val == null ? 0 : val;
    }

    public Long getNullableLongValue(Long val) throws DataFormatException {
        return val == null ? 0 : val;
    }

    public Integer getNullableIntegerValue(Integer val) throws DataFormatException {
        return val == null ? 0 : val;
    }


}
