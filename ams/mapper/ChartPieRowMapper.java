package com.ams.mapper;

import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Slice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;


public class ChartPieRowMapper
        implements RowMapper<Object> {

    public Slice[] mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<Slice> list = new ArrayList<>();
        list.add(Slice.newSlice(rs.getInt("VV"), Color.newColor("CACACA"), rs.getString("RATING")));
        return list.<Slice>toArray(new Slice[list.size()]);

    }

}