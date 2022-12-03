package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class BoardDAO {
    private final String BOARD_INSERT="insert into BOARD1 (title, writer, content,category) values (?,?,?,?)";
    private final String BOARD_UPDATE="update BOARD1 set title=?,writer=?,content=?,category=? where seq=?";
    private final String BOARD_DELETE="delete from BOARD1 where seq=?";
    private final String BOARD_GET="select * from BOARD1 where seq=?";
    private final String BOARD_LIST="select * from BOARD1 order by seq desc";
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertBoard(BoardVO vo){
        return jdbcTemplate.update(BOARD_INSERT,new Object[]{vo.getTitle(),vo.getWriter(),vo.getContent(),vo.getCategory()});

    }

    public int deleteBoard(int id){
        return jdbcTemplate.update(BOARD_DELETE,new Object[]{id});
    }
    public int updateBoard(BoardVO vo){
        return jdbcTemplate.update(BOARD_UPDATE,new Object[]{vo.getTitle(),vo.getWriter(),vo.getContent(),vo.getCategory(),vo.getSeq()});
    }
    public BoardVO getBoard(int seq){
        return jdbcTemplate.queryForObject(BOARD_GET,new Object[]{seq},new BeanPropertyRowMapper<BoardVO>(BoardVO.class));
    }
    public List<BoardVO> getBoardList(){
        return jdbcTemplate.query(BOARD_LIST, new RowMapper<BoardVO>() {
            @Override
            public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                BoardVO vo = new BoardVO();
                vo.setSeq(rs.getInt("seq"));
                vo.setTitle(rs.getString("title"));
                vo.setWriter(rs.getString("writer"));
                vo.setContent(rs.getString("content"));
                vo.setCategory(rs.getString("category"));
                vo.setRegdate(rs.getDate("regdate"));
                return vo;
            }
        });
    }
}
