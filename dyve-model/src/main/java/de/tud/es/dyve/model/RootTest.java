package de.tud.es.dyve.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class RootTest {

  private static final String NETWORK_ID = "NetworkId";
  private static final Status STATUS = Status.ERROR;
  private static final Topology TOPOLOGY = Topology.CUSTOM;
  private static final String ORGANIZATION_ID = "Organization";


  private static ModelFactory factory;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    factory = ModelFactory.eINSTANCE;
  }

  private Root root;

  @Before
  public void setUp() throws Exception {
    root = factory.createRoot();
  }

  @After
  public void tearDown() throws Exception {
    root = null;
  }

  @Test
  public void testCreateOrganization() {
    // assertNull(root.createOrganization(null, STATUS));
    // assertNull(root.createOrganization(ORGANIZATION_ID, null));

    final Organization organization = root.createOrganization(ORGANIZATION_ID, STATUS);
    assertEquals(ORGANIZATION_ID, organization.getName());
    assertEquals(STATUS, organization.getStatus());
    assertEquals(root, organization.getRoot());
  }

  @Test
  public void testCreateSubstrateNetwork() {
    final Network network = root.createNetwork(NETWORK_ID, STATUS, TOPOLOGY, false);
    assertTrue(network instanceof SubstrateNetwork);
    assertEquals(NETWORK_ID, network.getName());
    assertEquals(STATUS, network.getStatus());
    assertEquals(TOPOLOGY, network.getTopology());
  }


  @Test
  public void testCreateVirtualNetwork() {
    final Network network = root.createNetwork(NETWORK_ID, STATUS, TOPOLOGY, true);
    assertTrue(network instanceof VirtualNetwork);
    assertEquals(NETWORK_ID, network.getName());
    assertEquals(STATUS, network.getStatus());
    assertEquals(TOPOLOGY, network.getTopology());
  }


  @Test
  public void testMasterFailover() {
    // final Network network = root.createNetwork(NETWORK_ID, STATUS, TOPOLOGY, false);
    //
    // String masterName = "master";
    // final Server master = network.createServer(masterName, STATUS, CPU, MEMORY, STORAGE, DEPTH);
    // master.setName("master");
    // network.getNodes().add(master);
    //
    // final Server failover = factory.createServer();
    // failover.setName("failover");
    // network.getNodes().add(failover);
    //
    // assertTrue(root.createFailoverNode("master", "failover"));
    // assertEquals(master, failover.getMaster());
    // assertEquals(failover, master.getFailover());
  }

}
