package com.kffb.analyzer.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Lineup {
	
	private int lineupId;
	private int userId;
	private float projectedScore;
	private String siteName;
	private String teamName;

	public Lineup() {
		// TODO Auto-generated constructor stub
	}
	
	public Lineup(ResultSet rs) throws SQLException {
		this.lineupId = rs.getInt("L_ID");
		this.userId = rs.getInt("USER_ID");
		this.siteName = rs.getString("SITE_NAME");
		this.projectedScore = 100.0f;
		this.teamName = "Boss";
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public float getProjectedScore() {
		return projectedScore;
	}

	public void setProjectedScore(float projectedScore) {
		this.projectedScore = projectedScore;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getLineupId() {
		return lineupId;
	}

	public void setLineupId(int lineupId) {
		this.lineupId = lineupId;
	}

}
