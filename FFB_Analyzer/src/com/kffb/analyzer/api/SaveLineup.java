package com.kffb.analyzer.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class SaveLineup
 */
@WebServlet(description = "API - Save Lineup", urlPatterns = "/api/savelineup")
public class SaveLineup extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource(name="jdbc/ffbp")
	private DataSource ds;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveLineup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// will need to add logic to verify token is valid
		//
		// params - USER_ID (int), LINEUP_ID (int), PLAYERS (array of int)
		try {
			Connection conn = ds.getConnection();
			conn.setAutoCommit(false);
			int userId = Integer.valueOf(request.getParameter("userId"));
			
			Statement stmt = conn.createStatement();
			String query;
			int lineupId;
			if (request.getParameter("lineupId") == null) {
				query = String.format("INSERT INTO lineups (USER_ID) VALUES (%1$d);",userId);
				lineupId = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			} else {
				lineupId = Integer.valueOf(request.getParameter("lineupId"));
				query = String.format("UPDATE lineup_players SET ACTIVE=false WHERE LP_ID=%1$d;", lineupId);
				stmt.executeQuery(query);
			}
			
			JSONArray players = new JSONArray(request.getParameter("players"));
			// 1-user id, 2-lineupid (may be null), 3-playerid (using the site id)
			PreparedStatement updatePS = conn.prepareStatement("CALL updateLineup(?, ?, ?, ?)");
			
			String siteName = request.getParameter("site");
			
			for (int i = 0; i < players.length(); i++) {
				// set ID
				updatePS.setInt(1, userId);

				// set lineup ID
				updatePS.setInt(2, lineupId);
			
				// set player ID
				updatePS.setInt(3, players.getInt(i));
				
				updatePS.setString(4, siteName);
				
				updatePS.addBatch();
				
				System.out.println("CALL updateLineup("+userId+","+lineupId+","+players.getInt(i)+","+siteName+");");
			}
			int [] projRows = updatePS.executeBatch();
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			JSONObject jsonResponse = new JSONObject("{success : true}");
			int totalUpdates = 0;
			for (int i = 0; i < projRows.length; i++) {
				totalUpdates += projRows[i];
			}
			jsonResponse.append("projrows", totalUpdates);
			out.print(jsonResponse);
			out.flush();
			//use conn
			conn.commit();
			conn.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
