/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import br.ifce.brain.Symbol;
import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.IntelligentMind;
import br.ifce.mind.Mind;
import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.Action;

public class AgentTest {
	
	public static void dontImpress() {
		Agent agent = new Ceed("hello");
		agent.see("print", "Hi!");
		agent.see("set", new Object[] {"print", new AbstractAction() {
			public void act(Object args, Object callback) {
				System.out.println(args);
			}
		}});
		agent.see("print", "Hi!");
	}
	
	
	
	public Agent tester;
	public Agent agent;

	public AgentTest() {
	}
	public void init() {
		tester = new Ceed(new NaiveMind("tester"));
		
		tester.see("set", new Object [] {"assertEquals", new AbstractAction() {
			public void act(Object object, Object callback) {
				Object [] args = (Object []) object;
				boolean test;
				try {
					test = args[0].equals(args[1]);
				} catch (NullPointerException e) {
					test = (args[0] == args[1]);
				}
				if (!test) {
System.out.println("\n\nErro!! " + Arrays.toString(args));
					this.getAgent().see("set", new Object [] {"testError", Arrays.toString(args)});
				}
				else {
System.out.println("\n\nSucesso!! " + Arrays.toString(args));
					
				}
				this.getAgent().see("set", new Object [] {"test", test});
			}
		}});
		tester.see("set", new Object [] {"print", new AbstractAction() {
			public void act(Object object, Object callback) {
				System.out.println("ap: " + object);
			}
		}});
		tester.see("set", new Object [] {"showTestResults", new AbstractAction() {
			public void act(Object showBrains, Object callback) {
				tester.see("getAll", "test", new AbstractAction() {
					public void act(Object object, Object callback) {
						ArrayList<Boolean> results = (ArrayList<Boolean>) object;
						int success = 0;
						for (Boolean test : results) {
							if (test) {
								success++;
							}
						}
						int successCount = success;
						tester.see("getAll", "newTest", new AbstractAction() {
							public void act(Object object, Object callback) {
								ArrayList<Boolean> tests = (ArrayList<Boolean>) object;
								int total = tests.size();
								tester.see("print", "Tester: " + tester + "(" + tester.getMind() + ")");
								tester.see("print", "Tested: " + agent + "(" + agent.getMind() + ")");
								tester.see("print", "Test results: " + successCount + "/" + total);
								
								if (successCount != total) {
									tester.see("print", "Results: " + results);
									tester.see("print", "Errors:");
									tester.see("getAll", "testError", "print");
								}
								
								if (showBrains != null && (Boolean) showBrains) {
									System.out.println(((NaiveMind) tester.getMind()).getBrain());
									System.out.println(((NaiveMind) agent.getMind()).getBrain());
								}
							}
						});
						
					}
				});
			}
		}});

	}
	class AssertResult extends AbstractAction {
		Object sum;
		public AssertResult(Object sum) {
			this.sum = sum;
			tester.see("set", "newTest=1");
		}
		public void act(Object s, Object callback) {
			tester.see("assertEquals", new Object [] {s, sum});
		}
	}
	public void simplePrint() {
		// No response
		agent = new Ceed(new NaiveMind());
		agent.see("print", "oi");
		
		agent.see("set", new Object [] {"print", new AbstractAction() {
			public void act(Object object, Object callback) {
				System.out.println(this.getAgent() + " printing: " + object);
				this.doCallback(callback, null);
			}
		}});

		// Now we see!
		agent.see("print", "oi");
		agent.see("print", "oi", new AbstractAction() {
			public void act(Object object, Object callback) {
				System.out.println("attempt callback: " + object);
			}
		});
		
	}
	public void mathTest() {
		agent = new Ceed(new NaiveMind("math"));
		// Sum should fail!
		agent.see("sum", new Object [] {1f, 2f}, new AssertResult(null));

		// Teaching techniques
		agent.see("set", new Object [] {"sum", new AbstractAction() {
			public void act(Object object, Object callback) {
				float soma = 0.0f;
				for (Object n: (Object []) object) {
					soma += (Float) n;
				}
				this.doCallback(callback, soma);
			}
		}});
		agent.see("set", new Object [] {"mult", new AbstractAction() {
			public void act(Object object, Object callback) {
				float soma = 1.0f;
				for (Float n: (Float []) object) {
					soma *= n;
				}
				this.doCallback(callback, soma);
			}
		}});
		
		// Sum should succeed!
		agent.see("sum", new Object [] {1f, 2f}, new AssertResult(3f));

		Random random = new Random();
		for (int i = 1; i < 11; i++) {
			int tam = random.nextInt(10);
			Float [] numbers = new Float [tam];
			float sum = 0.0f;
			float mult = 1.0f;
			for (int j = 0; j < tam; j++) {
				float n = random.nextFloat();
				sum += n;
				mult *= n;
				numbers[j] = n;
			}
			agent.see("sum", numbers, new AssertResult(sum));
			agent.see("mult", numbers, new AssertResult(mult));
		}
		
		tester.see("showTestResults", true);
	}


	public void testInheritance() {
		agent = new Ceed(new NaiveMind(new String [] {"one", "two", "three", "a.b", "a.b.c"}));
		agent.see("print", "oi");
		/**/
		agent.see("set", new Object [] {"print", new AbstractAction() {
			public void act(Object object, Object callback) {
				doCallback(callback, "one.print: " + object);
			}
		}});
		agent.see("set", new Object [] {"two.print", new AbstractAction() {
			public void act(Object object, Object callback) {
				doCallback(callback, "two.print: " + object);
			}
		}});
		agent.see("set", new Object [] {"four.print", new AbstractAction() {
			public void act(Object object, Object callback) {
				this.getAgent().see("two.print", "four.print: " + object, callback);
			}
		}});
		agent.see("set", new Object [] {"a.b.c.print", new AbstractAction() {
			public void act(Object object, Object callback) {
				doCallback(callback, "a.b.c.print: " + object);
			}
		}});
		agent.see("set", new Object [] {"a.b.c.read", new AbstractAction() {
			public void act(Object object, Object callback) {
				doCallback(callback, "a.b.c.read: " + object);
			}
		}});
		agent.see("set", new Object [] {"three.imprime", new AbstractAction() {
			public void act(Object object, Object callback) {
				doCallback(callback, "three.imprime: " + object);
			}
		}});
		agent.see("set", new Object [] {"two.imprime", new AbstractAction() {
			public void act(Object object, Object callback) {
				doCallback(callback, "two.imprime: " + object);
			}
		}});
		agent.see("set", new Object [] {"getName", new AbstractAction() {
			public void act(Object object, Object callback) {
				this.getAgent().see("super.getName", object, new AbstractAction() {
					public void act(Object target, Object callback2) {
						doCallback(callback, "new: " + target);
					}
				});
			}
		}});
		testAgentInheritance(agent);
	}
	public void testCEInheritance() {
		agent = Ceed.getAgent("testInheritance");
		testAgentInheritance(agent);
	}
	public void testAgentInheritance(Agent agent) {
		agent.see("print", "oi", new AssertResult("one.print: oi"));
		agent.see("two.print", "oi", new AssertResult("two.print: oi"));
		agent.see("four.print", "oi", new AssertResult("two.print: four.print: oi"));
		agent.see("a.b.c.print", "oi", new AssertResult("a.b.c.print: oi"));
		agent.see("imprime", "oi", new AssertResult("two.imprime: oi"));
		agent.see("three.imprime", "oi", new AssertResult("three.imprime: oi"));
		agent.see("read", "oi", new AssertResult("a.b.c.read: oi"));
		agent.see("c.print", "oi", new AssertResult(null));
		agent.see("asdf.print", "oi", new AssertResult(null));
		agent.see("super.print", "oi", new AssertResult("two.print: oi"));
		agent.see("getName", null, new AssertResult("new: one"));
		agent.see("super.getName", null, new AssertResult("one"));
		
		tester.see("showTestResults", false);
		
		tester.see("get", "asdf", "print");
		tester.see("getAll", "asdf", "print");
		
		System.out.println(((NaiveMind) agent.getMind()).getBrain());
	}
	public void testOverwriting(Agent agent) {
		agent.see("overwriteTest", "oi", new AssertResult(null));
		agent.see("set", new Object [] {"overwriteTest", new AbstractAction() {
			public void act(Object target, Object callback) {
				doCallback(callback, target);
			}
		}});
		agent.see("overwriteTest", "oi", new AssertResult("oi"));
		agent.see("set", new Object [] {"overwriteTest", new AbstractAction() {
			public void act(Object target, Object callback) {
				doCallback(callback, 1);
			}
		}});
		agent.see("overwriteTest", "oi", new AssertResult(1));
		agent.see("set", new Object [] {"overwriteTest", new AbstractAction() {
			public void act(Object target, Object callback) {
				doCallback(callback, target + "1");
			}
		}});
		agent.see("overwriteTest", "oi", new AssertResult("oi1"));
	}
	
	public static void testName() {
		Agent agent = new Ceed("");
		agent.see("getNames", null, new AbstractAction() {
			public void act(Object target, Object callback) {
				System.out.println(target);
				System.out.println(((NaiveMind) this.getAgent().getMind()).getBrain());
			}
		});
		agent.see("super.getNames", null, new AbstractAction() {
			public void act(Object target, Object callback) {
				System.out.println("2 " + target);
			}
		});
	}
	public static void intelligenceTest() {
		Mind test = new NaiveMind("tester");
		//Agent test = CEED.getAgent("tester").getAgent();
		Action assertEquals = new AbstractAction() {
			public void act(Object target, Object callback) {
				Object [] args = (Object []) target;
				try {
					System.out.println(this.getAgent() + "(" + args[0] + " == " + args[1] + "): " + (args[0] == args[1] || args[0].equals(args[1])));
				} catch (Exception e) {
					System.out.println(this.getAgent() + "(" + args[0] + " == " + args[1] + "): " + false);
				}
			}
		};
		test.see(new Symbol("set", new Object [] {"assertEquals", assertEquals}));

		class AssertEquals extends AbstractAction {
			Object value;
			public AssertEquals(Object value) {
				this.value = value;
			}
			public void act(Object target, Object callback) {
				Object [] args = new Object [] {target, value};
				try {
					System.out.println(this.getAgent() + "(" + args[0] + " == " + args[1] + "): " + (args[0] == args[1] || args[0].equals(args[1])));
				} catch (Exception e) {
					System.out.println(this.getAgent() + "(" + args[0] + " == " + args[1] + "): " + false);
				}
			}
		};
		
		/**
		 * Choose your agent
		 */
		//Mind joe = (Mind) CEED.getAgent("joe").getAgent();
		IntelligentMind joe = new NaiveMind("joe");
		//Mind joe = new DummyAgent();
		
		// Sum should fail!
		joe.see(new Symbol("sum", new float [] {1, 2}), new AssertEquals(3f));

		// Teaching techniques
		joe.set("sum", new AbstractAction() {
			public void act(Object object, Object callback) {
				float soma = 0f;
				for (float n: (float []) object) {
					soma += n;
				}
				this.doCallback(callback, soma);
			}
		});

		// Sum should succeed!
		joe.see(new Symbol("sum", new float [] {1, 2}), new AssertEquals(3f));
	}

	public static void main(String[] args) {
		//AgentTest test = new AgentTest();
		/*
		AgentBody agent = CE.getAgent("overwrite");
		test.agent = agent;

		test.testAgentInheritance(agent);
		
		test.tester.see("showTestResults", true);*/
		//test.testInheritance();
		intelligenceTest();
		
	}

}
