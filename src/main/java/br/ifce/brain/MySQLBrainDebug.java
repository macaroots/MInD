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
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class MySQLBrainDebug implements Brain {
	
	private static final String C_Z = "z";
	private static final String C_INFO = "info";
	private static final String C_A = "a";
	private static final String C_B = "b";
	private static final String C_R = "r";
	private static final String TB_SIMBOLOS = "symbols";
	private static final String TB_NOS = "nodes";
	private String host, database, user, password;
	public boolean debug = false;
	private Connection conn;
	private static DataSource datasource;

private int total = 0;
	
	public MySQLBrainDebug() {
		this(null, null, null, null);
	}
	public MySQLBrainDebug(String host, String database, String user, String password) {
		setHost(host);
		setDatabase(database);
		setUser(user);
		setPassword(password);
		 
		PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://" + host + ":3306/" + database);
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername(user);
        p.setPassword(password);
    	p.setJmxEnabled(false);
    	p.setTestWhileIdle(true);
    	p.setTestOnBorrow(true);
    	p.setValidationQuery("SELECT 1");
    	p.setTestOnReturn(true);
    	p.setValidationInterval(30000);
    	p.setTimeBetweenEvictionRunsMillis(30000);
    	p.setMaxActive(100);
    	p.setInitialSize(10);
    	p.setMaxWait(10000);
    	p.setRemoveAbandonedTimeout(60);
    	p.setMinEvictableIdleTimeMillis(30000);
    	p.setMinIdle(10);
    	p.setLogAbandoned(false);
    	p.setRemoveAbandoned(true);
        if (datasource == null) {
        	datasource = new DataSource();
        }
        datasource.setPoolProperties(p); 
	}
	
	public Connection getConnection() {
total++;
		try {
			if (this.debug) {
				System.err.println("getConnection: " + datasource.getUrl());
			}
			
            conn = datasource.getConnection();
		} catch (SQLException e) {
			new RuntimeException("Error while connecting!", e);
		}
		return conn;
	}
	protected void finalize() throws Throwable {
		try { if (conn != null) conn.close(); } catch(Exception e) {
			System.out.println("Error closing conn: " + e.getMessage());
		}
		try {
			if (datasource != null) {
				datasource.close();
			}
		} catch(Exception e) {
			System.out.println("Error closing datasource: " + e.getMessage());
		}
		super.finalize();
	}
	protected void close(PreparedStatement st, Connection conn) {
		close(null, st, conn);
	}
	protected void close(ResultSet rs, PreparedStatement st, Connection conn) {
total--;
		try { if (rs != null) rs.close(); } catch(Exception e) {
			System.out.println("Error closing rs: " + e.getMessage());
		}
		try { if (st != null) st.close(); } catch(Exception e) {
			System.out.println("Error closing st: " + e.getMessage());
		}
		try {
			if (conn != null) {
System.out.println("\n\nCheck1 (" + total + ")  " + conn + ", " + conn.isClosed());

List<String> l = new ArrayList<>();
l.add("br.ifce.mind.actions.naive.Act.act(Act.java:25)");
l.add("br.ifce.mind.NaiveAgent.act(NaiveAgent.java:125)");
l.add("br.ifce.mind.actions.naive.See.act(See.java:34)");
l.add("br.ifce.mind.actions.naive.See.act(See.java:20)");
l.add("br.ifce.mind.NaiveAgent.see(NaiveAgent.java:105)");
l.add("br.ifce.mind.AgentBody.see(AgentBody.java:20)");
l.add("br.ifce.mind.AgentBody.see(AgentBody.java:17)");
l.add("br.ifce.mind.actions.AbstractAction.doCallback(AbstractAction.java:33)");
l.add("br.ifce.mind.actions.naive.Get.act(Get.java:19)");
l.add("br.ifce.mind.actions.naive.See$1.act(See.java:46)");
l.add("br.ifce.mind.actions.naive.GetAll$1.act(GetAll.java:73)");
l.add("br.ifce.mind.actions.naive.ReadBrain.act(ReadBrain.java:47)");
l.add("br.ifce.mind.actions.naive.GetAll.act(GetAll.java:61)");
l.add("br.ifce.mind.NaiveAgent.get(NaiveAgent.java:115)");
l.add("br.ifce.mind.actions.naive.See.act(See.java:37)");
l.add("br.tiia.ce.actions.GetLibraries$1$1$1$1.act(GetLibraries.java:36)");
l.add("br.tiia.ce.actions.Require$1$1.act(Require.java:59)");
l.add("br.ifce.mind.actions.naive.Get$1.act(Get.java:26)");
l.add("br.tiia.ce.actions.Require$1.act(Require.java:34)");
l.add("br.ifce.mind.actions.naive.Get$1.act(Get.java:26)");
l.add("br.tiia.ce.actions.Require.act(Require.java:28)");
l.add("br.tiia.ce.actions.GetLibraries$1$1$1.act(GetLibraries.java:33)");

int c = 0;
int over = 0;
StackTraceElement[] trace = Thread.currentThread().getStackTrace();
System.out.print("Total: " + trace.length + ", " + over);
for (StackTraceElement s: trace) {
	if (l.contains(s.toString())) {
		over++;
		continue;
	}
	if (c++ < 30) {
		System.out.println(c + ": " + s);
//		break;
	}
}
				((javax.sql.PooledConnection) conn).close();
System.out.print("Total: " + trace.length + ", " + over);
System.out.println("\n\nCheck2  " + conn + ", " + conn.isClosed() + "\n\n");
				conn = null;
			}
		} catch (SQLException e) {
			new RuntimeException("Error on closing!", e);
		}
	}

	public void set(Symbol s) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			if (s.getId() == 0) {
				String sql = "insert into " + TB_SIMBOLOS + " (" + C_Z + ", " + C_INFO + ") values (?, ?)";
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
					updates += ", " + C_Z + " = '" + s.getType() + "'";
	 			}
				if (s.getInfo() != null) {
					updates += ", " + C_INFO + " = '" + s.getInfo() + "'";
	 			}
				String sql = "update " + TB_SIMBOLOS + " set id = " + s.getId() + updates + " where id = " + s.getId();
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
				String sql = "delete from " + TB_SIMBOLOS + " where id in (" + ids + ")";
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
				symbol.setType(rs.getString(C_Z));
				symbol.setInfo(rs.getObject(C_INFO));
				symbols.add(symbol);
			}
		} catch (SQLException e) {
			new RuntimeException("Error while getting!", e);
		} finally {
			close(rs, st, conn);
		}
		
		return symbols;
	}

	public void tie(Link n) {
		PreparedStatement st = null;
		try {
			if (n.getA().getId() == 0) {
				this.set(n.getA());
			}
			if (n.getR().getId() == 0) {
				this.set(n.getR());
			}
			if (n.getB().getId() == 0) {
				this.set(n.getB());
			}
			
			String sql = "insert into " + TB_NOS + " (" + C_A + ", " + C_B + ", " + C_R + ") values (?, ?, ?)";
			if (this.debug) {
				String values = n.getA().getId() + ", " + n.getB().getId() + ", " + n.getR().getId();
				System.out.println(sql + " % " + values);
			}
			conn = getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, n.getA().getId());
			st.setInt(2, n.getB().getId());
			st.setInt(3, n.getR().getId());
			st.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			new RuntimeException("Error while tying!", e);
		} finally {
			close(st, conn);
		}
	}

	public List<Link> untie(Link n) {
		List<Link> nodes;
		//String busca = this.buscaNos(n);
		//String sql = "delete from " + TB_NOS + " where " + busca;
		Object [] args = this.nodeSql(n, "delete");
		String sql = (String) args[0];
		List<Object> values = (List<Object>) args[1];
		if (this.debug) {
			System.out.println(sql + " % " + values);
		}
		PreparedStatement st = null;
		try {
			nodes = this.reason(n);
			conn = getConnection();
			st = conn.prepareStatement(sql);
			this.setParameters(st, values);
			st.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			nodes = null;
			new RuntimeException("Error while untying!", e);
		} finally {
			close(st, conn);
		}
		
		return nodes;
	}

	protected Symbol getSymbolFromRS(String role, ResultSet rs) throws SQLException {
		Symbol symbol = new Symbol();
		symbol.setId(rs.getInt(role + "_id"));
		symbol.setType(rs.getString(role + "_" + C_Z));
		symbol.setInfo(rs.getObject(role + "_" + C_INFO));
		return symbol;
	}
	public List<Link> reason(Link n) {
		List<Link> links = new ArrayList<Link>();

		Object [] args = this.nodeSql(n);
		String sql = (String) args[0];
		List<Object> values = (List<Object>) args[1];
		if (this.debug) {
			System.out.println(sql + " % " + values);
		}
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
			sql += " and z like ?";
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
			sql += " and " + role + ".z like ?";
			values.add(s.getType());
		}
		if (s.getInfo() != null) {
			sql += " and " + role + ".info like ?";
			values.add(s.getInfo());
		}
		return new Object [] {sql, values};
	}

	protected Object[] nodeSql(Link n) {
		return this.nodeSql(n, "a.id as a_id, a.z as a_z, a.info as a_info,"
				+ "r.id as r_id, r.z as r_z, r.info as r_info,"
				+ "b.id as b_id, b.z as b_z, b.info as b_info, n.*");
	}
	protected Object[] nodeSql(Link n, String select) {
		String sql = "select " + select + " from nodes n "
				+ "inner join symbols a on n.a = a.id "
				+ "inner join symbols b on n.b = b.id "
				+ "inner join symbols r on n.r = r.id where 1=1";
		List<Object> values = new ArrayList<Object>();
		if (n != null) {
			if (n.getA() != null) {
				Object [] args = this.symbolReasonSql(n.getA(), "a");
				List<Object> subvalues = (List<Object>) args[1];
				if (!subvalues.isEmpty()) {
					sql += args[0];
					values.addAll(subvalues);
				}
			}
			if (n.getB() != null) {
				Object [] args = this.symbolReasonSql(n.getB(), "b");
				List<Object> subvalues = (List<Object>) args[1];
				if (!subvalues.isEmpty()) {
					sql += args[0];
					values.addAll(subvalues);
				}
			}
			if (n.getR() != null) {
				Object [] args = this.symbolReasonSql(n.getR(), "r");
				List<Object> subvalues = (List<Object>) args[1];
				if (!subvalues.isEmpty()) {
					sql += args[0];
					values.addAll(subvalues);
				}
			}
		}
		return new Object [] {sql, values};
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
