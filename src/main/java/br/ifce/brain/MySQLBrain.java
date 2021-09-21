/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.brain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;

public class MySQLBrain implements Brain {
	
	public boolean debug = false;
	private DataSource datasource;
	
	public MySQLBrain() {
		this("localhost", "jmind", "root", "", "3306");
	}
	public MySQLBrain(String host, String database, String user, String password, String port) {
		if (datasource == null) {
			PoolProperties p = new PoolProperties();
			p.setUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC");
			p.setDriverClassName("com.mysql.cj.jdbc.Driver");
			p.setUsername(user);
			p.setPassword(password);
			
			p.setJmxEnabled(true);
			p.setTestWhileIdle(false);
			p.setTestOnBorrow(true);
			p.setValidationQuery("SELECT 1");
			p.setTestOnReturn(false);
			p.setValidationInterval(30000);
			p.setTimeBetweenEvictionRunsMillis(30000);
			p.setMaxActive(100);
			p.setInitialSize(10);
			p.setMaxWait(10000);
			p.setRemoveAbandonedTimeout(60);
			p.setMinEvictableIdleTimeMillis(30000);
			p.setMinIdle(10);
			p.setLogAbandoned(true);
			p.setRemoveAbandoned(true);
			p.setJdbcInterceptors(
					"org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
					"org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
			org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
			datasource.setPoolProperties(p);
			this.setDatasource(datasource);
			
			System.out.println("\n\n**************\nDatasource created! " + datasource + "\n\n");
			

			createTables();
		}
	}
	public MySQLBrain(DataSource ds) {
		setDatasource(ds);
		createTables();
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			if (this.debug) {
				System.out.println("getConnection: " + ((org.apache.tomcat.jdbc.pool.DataSource) datasource).getUrl());
			}
			
            conn = getDatasource().getConnection();
			if (this.debug) {
				System.out.println("connection: " + conn);
			}
		} catch (SQLException e) {
			System.err.println("SQLError while connecting!" + e + ": " + e.getMessage());
			new RuntimeException("SQLError while connecting!", e);
		} catch (Exception e) {
			System.err.println("Error while connecting!" + e + ": " + e.getMessage());
			new RuntimeException("Error while connecting!", e);
		}
		return conn;
	}
	public void createTables() {
		String sqlSymbols = "CREATE TABLE IF NOT EXISTS `symbols` ("
		+ "`id` int(11) NOT NULL AUTO_INCREMENT,"
		+ "`type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"
		+ "`info` mediumtext COLLATE utf8_unicode_ci,"
		+ "`data` timestamp NULL DEFAULT CURRENT_TIMESTAMP,"
		+ "PRIMARY KEY (`id`))";

		String sqlLinks = "CREATE TABLE IF NOT EXISTS `links` ("
				+ "`id` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`a` int(11) DEFAULT NULL,"
				+ "`r` int(11) DEFAULT NULL,"
				+ "`b` int(11) DEFAULT NULL,"
				+ "`data` timestamp NULL DEFAULT CURRENT_TIMESTAMP,"
				+ "PRIMARY KEY (`id`),"
				+ "KEY `idx_nodes_a` (`a`),"
				+ "KEY `idx_nodes_r` (`r`),"
				+ "KEY `idx_nodes_b` (`b`))";
		
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			st.execute(sqlSymbols);
			st.execute(sqlLinks);
		} catch (Exception e) {
			System.err.println("SQLError while creating tables!" + e + ": " + e.getMessage());
			new RuntimeException("SQLError while creating tables!", e);
		}
		finally {
			close(st, conn);
		
		}
	}
	protected void finalize() throws Throwable {
		try {
			if (datasource != null) {
				((org.apache.tomcat.jdbc.pool.DataSource) datasource).close();
				System.out.println("Datasource closed!");
			}
		} catch(Exception e) {
			System.err.println("Error closing datasource: " + e.getMessage());
		}
		super.finalize();
	}
	protected void close(Statement st, Connection conn) {
		close(null, st, conn);
	}
	protected void close(ResultSet rs, Statement st, Connection conn) {
		try { if (rs != null) rs.close(); } catch(Exception e) {
			System.err.println("Error closing rs: " + e.getMessage());
		}
		try { if (st != null) st.close(); } catch(Exception e) {
			System.err.println("Error closing st: " + e.getMessage());
		}
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			new RuntimeException("Error on closing!", e);
		}
	}

	public void set(Symbol s) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			if (s.getId() == 0) {
				String sql = "insert into symbols (type, info) values (?, ?)";
				if (this.debug) {
					String values = s.getType()  + ", " + s.getInfo();
					System.out.println(sql + " % " + values);
				}
				st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				st.setString(1, s.getType().toString());
				st.setString(2, s.getInfo().toString());
				st.executeUpdate();
				
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					s.setId(rs.getInt(1));
				}
			}
			else {
				String updates = "";
				if (s.getType() != null) {
					updates += ", type = '" + s.getType() + "'";
	 			}
				if (s.getInfo() != null) {
					updates += ", info = '" + s.getInfo() + "'";
	 			}
				String sql = "update symbols set id = " + s.getId() + updates + " where id = " + s.getId();
				if (this.debug) {
					System.out.println(sql);
				}
				st = conn.prepareStatement(sql);
				st.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			new RuntimeException("Error while setting!", e);
		} finally {
			close(rs, st, conn);
		}
	}

	public List<Symbol> forget(Symbol s) {
		List<Symbol> symbols;
		Connection conn = null;
		PreparedStatement st = null;
		try {
			symbols = this.get(s);
			if (!symbols.isEmpty()) {
				conn = getConnection();
				String ids = "";
				String and = "";
				for (Symbol symbol: symbols) {
					ids += and + symbol.getId();
					and = ", ";
				}
				String sql = "delete from symbols where id in (" + ids + ")";
				if (this.debug) {
					System.out.println(sql);
				}
				st = conn.prepareStatement(sql);
				st.executeUpdate();
				conn.commit();
			}
		} catch (SQLException e) {
			symbols = null;
			new RuntimeException("Error while forgetting!", e);
		} finally {
			close(st, conn);
		}
		
		return symbols;
	}

	public List<Symbol> get(Symbol s) {
		List<Symbol> symbols = new ArrayList<Symbol>();

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//String busca = this.buscaSimbolos(s);
			//String sql = "select * from " + TB_SIMBOLOS + " where 1=1 and " + busca;
			Object [] args = this.symbolSql(s);
			String sql = (String) args[0];
			List<Object> values = (List<Object>) args[1];
			if (this.debug) {
				System.out.println(sql + " % " + values);
			}
			conn = getConnection();
			st = conn.prepareStatement(sql);
			this.setParameters(st, values);
			rs = st.executeQuery();
			while (rs.next()) {
				Symbol symbol = new Symbol();
				symbol.setId(rs.getInt("id"));
				symbol.setType(rs.getString("type"));
				symbol.setInfo(rs.getObject("info"));
				symbol.setDate(rs.getDate("data"));
				symbols.add(symbol);
			}
		} catch (SQLException e) {
			new MySQLBrainException("Error getting " + s + "!", e);
		} finally {
			close(rs, st, conn);
		}
		
		return symbols;
	}

	public void tie(Link l) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			if (l.getA().getId() == 0) {
				this.set(l.getA());
			}
			if (l.getR().getId() == 0) {
				this.set(l.getR());
			}
			if (l.getB().getId() == 0) {
				this.set(l.getB());
			}
			
			String sql = "insert into links (a, r, b) values (?, ?, ?)";
			if (this.debug) {
				String values = l.getA().getId() + ", " + l.getB().getId() + ", " + l.getR().getId();
				System.out.println(sql + " % " + values);
			}
			conn = getConnection();
			st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			st.setInt(1, l.getA().getId());
			st.setInt(2, l.getR().getId());
			st.setInt(3, l.getB().getId());
			st.executeUpdate();

			rs = st.getGeneratedKeys();
			if (rs.next()) {
				l.setId(rs.getInt(1));
			}
			conn.commit();
		} catch (SQLException e) {
			new RuntimeException("Error while tying!", e);
		} finally {
			close(rs, st, conn);
		}
	}

	public List<Link> untie(Link l) {
		List<Link> links;
		Object [] args = this.linkSql(l, "delete");
		String sql = (String) args[0];
		List<Object> values = (List<Object>) args[1];
		if (this.debug) {
			System.out.println(sql + " % " + values);
		}
		Connection conn = null;
		PreparedStatement st = null;
		try {
			links = this.reason(l);
			conn = getConnection();
			st = conn.prepareStatement(sql);
			this.setParameters(st, values);
			st.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			links = null;
			new RuntimeException("Error while untying!", e);
		} finally {
			close(st, conn);
		}
		
		return links;
	}

	protected Symbol getSymbolFromRS(String role, ResultSet rs) throws SQLException {
		Symbol symbol = new Symbol();
		symbol.setId(rs.getInt(role + "_id"));
		symbol.setType(rs.getString(role + "_type"));
		symbol.setInfo(rs.getObject(role + "_info"));
		return symbol;
	}
	public List<Link> reason(Link l) {
		List<Link> links = new ArrayList<Link>();

		Object [] args = this.linkSql(l);
		String sql = (String) args[0];
		List<Object> values = (List<Object>) args[1];
		if (this.debug) {
			System.out.println(sql + " % " + values);
		}
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			st = conn.prepareStatement(sql);
			this.setParameters(st, values);
			rs = st.executeQuery();
			while (rs.next()) {
				Link link = new Link();
				link.setA(this.getSymbolFromRS("a", rs));
				link.setR(this.getSymbolFromRS("r", rs));
				link.setB(this.getSymbolFromRS("b", rs));
				links.add(link);
			}
		} catch (SQLException e) {
			System.err.println("Error while reasoning!" + e + e.getMessage());
			new RuntimeException("Error while reasoning!", e);
		} finally {
			close(rs, st, conn);
		}
		return links;

	}
	protected void setParameters(PreparedStatement st, List<Object> args) throws SQLException {
		int i = 1;
		for (Object arg: args) {
			try {
				st.setInt(i, (Integer) arg);
				i++;
			} catch (ClassCastException e) {
				st.setString(i, arg.toString());
				i++;
			}
		}
	}
	
	protected Object [] symbolSql(Symbol s) {
		return this.symbolSql(s, "*");
	}
	protected Object [] symbolSql(Symbol s, String select) {
		String sql = "select " + select + " from symbols where 1=1";
		List<Object> values = new ArrayList<Object>();
		if (s.getId() != 0) {
			sql += " and id = ?";
			values.add(s.getId());
		}
		if (s.getType() != null) {
			sql += " and type like ?";
			values.add(s.getType());
		}
		if (s.getInfo() != null) {
			sql += " and info like ?";
			values.add(s.getInfo());
		}
		return new Object [] {sql, values};
	}
	protected Object [] symbolReasonSql(Symbol s, String role) {
		String sql = "";
		List<Object> values = new ArrayList<Object>();
		if (s.getId() != 0) {
			sql += " and " + role + ".id = ?";
			values.add(s.getId());
		}
		if (s.getType() != null) {
			sql += " and " + role + ".type like ?";
			values.add(s.getType());
		}
		if (s.getInfo() != null) {
			sql += " and " + role + ".info like ?";
			values.add(s.getInfo());
		}
		return new Object [] {sql, values};
	}

	protected Object[] linkSql(Link l) {
		return this.linkSql(l, "a.id as a_id, a.type as a_type, a.info as a_info,"
				+ "r.id as r_id, r.type as r_type, r.info as r_info,"
				+ "b.id as b_id, b.type as b_type, b.info as b_info, l.*");
	}
	protected Object[] linkSql(Link l, String select) {
		String sql = "select " + select + " from links l "
				+ "inner join symbols a on l.a = a.id "
				+ "inner join symbols b on l.b = b.id "
				+ "inner join symbols r on l.r = r.id where 1=1";
		List<Object> values = new ArrayList<Object>();
		if (l != null) {
			if (l.getA() != null) {
				Object [] args = this.symbolReasonSql(l.getA(), "a");
				List<Object> subvalues = (List<Object>) args[1];
				if (!subvalues.isEmpty()) {
					sql += args[0];
					values.addAll(subvalues);
				}
			}
			if (l.getB() != null) {
				Object [] args = this.symbolReasonSql(l.getB(), "b");
				List<Object> subvalues = (List<Object>) args[1];
				if (!subvalues.isEmpty()) {
					sql += args[0];
					values.addAll(subvalues);
				}
			}
			if (l.getR() != null) {
				Object [] args = this.symbolReasonSql(l.getR(), "r");
				List<Object> subvalues = (List<Object>) args[1];
				if (!subvalues.isEmpty()) {
					sql += args[0];
					values.addAll(subvalues);
				}
			}
		}
		return new Object [] {sql, values};
	}
	public DataSource getDatasource() {
		return datasource;
	}
	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
}
