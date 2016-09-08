package com.kffb.analyzer.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class LoadProjections
 */
@WebServlet(description = "API - Load Projections", urlPatterns = "/api/loadprojections")

public class LoadProjections extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/ffbp")
	private DataSource ds;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadProjections() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement projPS = conn.prepareStatement("REPLACE INTO projections (P_ID,WEEK,ESPN_ID,ESPN_PROJ) VALUES (?, ?, ?, ?)");
			PreparedStatement playerPS = conn.prepareStatement("REPLACE INTO players (P_ID, PNAME) VALUES (?, ?)"); 
			
			JSONArray projections = new JSONArray(request.getParameter("projections"));
			JSONObject proj;
			for (int i = 0; i < projections.length(); i++) {
				proj = new JSONObject(projections.getString(i));
				// set ID
				projPS.setInt(1, proj.getInt("ESPNID"));
				playerPS.setInt(1, proj.getInt("ESPNID"));
				
				// set Week
				projPS.setInt(2, Integer.valueOf(request.getParameter("week")));
				
				// set ESPN ID
				projPS.setInt(3, proj.getInt("ESPNID"));
				
				// set ESPN Proj
				projPS.setInt(4, proj.getInt("PROJ"));
				
				// set Player Name
				playerPS.setString(2, proj.getString("NAME"));
				projPS.addBatch();
				playerPS.addBatch();
				System.out.println(proj.getString("NAME"));
			}
			int [] projRows = projPS.executeBatch();
			int [] playerRows = playerPS.executeBatch();
			
			System.out.println("Proj Rows : " + projRows.toString());
			System.out.println("Player rows : " + playerRows.toString());
			
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			JSONObject jsonResponse = new JSONObject("{success : true}");
			jsonResponse.append("projrows", projRows.toString());
			jsonResponse.append("playerrows", playerRows.toString());
			out.print(jsonResponse);
			out.flush();
			//use conn
			conn.commit();
			conn.close();
		} catch (JSONException je) {
			je.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

}
