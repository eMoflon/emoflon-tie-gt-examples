package de.tud.es.dyve.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NetworkTest {

	// Substrate network values
	private static final String SUB_NET_ID = "SubstrateNetwork";
	private static final Status SUB_STATUS = Status.ACTIVE;
	private static final Topology SUB_TOPOLOGY = Topology.CUSTOM;
	private static final String SUB_SWITCH = "SubstrateSwitch";
	private static final String SUB_SERVER_1 = "SubstrateServer1";
	private static final String SUB_SERVER_2 = "SubstrateServer2";
	private static final String SUB_LINK_1_TO_2 = "SubstrateLink_Server1_To_Server2";

	// Virtual network values
	private static final String VIR_NET_ID = "VirtualNetwork";
	private static final Status VIR_STATUS = Status.ACTIVE;
	private static final Topology VIR_TOPOLOGY = Topology.CUSTOM;
	private static final String VIR_SWITCH = "VirtualSwitch";
	private static final String VIR_LINK_1_TO_2 = "VirtualLink_Server1_To_Server2";
	private static final String VIR_SERVER_2 = "VirtualServer2";
	private static final String VIR_SERVER_1 = "VirtualServer1";

	// Common network values
//  private static final String NETWORK_ID = "NetworkId";
	private static final Status STATUS = Status.INACTIVE;
//  private static final Topology TOPOLOGY = Topology.CUSTOM;
	private static final int CPU = 2;
	private static final int MEMORY = 4;
	private static final int STORAGE = 6;
	private static final int BANDWIDTH = 10;
	private static final int DEPTH = 2;

	private static ModelFactory factory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = ModelFactory.eINSTANCE;
	}

	private Root root;
	private Network subNet;
	private Network virNet;

	@Before
	public void setUp() throws Exception {
		root = factory.createRoot();
		subNet = root.createNetwork(SUB_NET_ID, SUB_STATUS, SUB_TOPOLOGY, false);
		virNet = root.createNetwork(VIR_NET_ID, VIR_STATUS, VIR_TOPOLOGY, true);
	}

	@After
	public void tearDown() throws Exception {
		root = null;
	}

	@Test
	public void testCreateSubstrateFailoverNode() {
		final Server master = subNet.createServer(SUB_SERVER_1, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		final Server failover = subNet.createServer(SUB_SERVER_2, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		assertNull(master.getFailover());
		assertNull(master.getMaster());
		assertNull(failover.getMaster());
		assertNull(failover.getFailover());

		assertTrue(subNet.createFailoverNode(master.getName(), failover.getName()));
		assertEquals(failover, master.getFailover());
		assertNull(master.getMaster());
		assertEquals(master, failover.getMaster());
		assertNull(failover.getFailover());
	}

	@Test
	public void testCreateSubstrateLink() {
		assertNull(subNet.createLink(SUB_LINK_1_TO_2, null, SUB_SERVER_2, BANDWIDTH, STATUS));
		assertNull(subNet.createLink(SUB_LINK_1_TO_2, SUB_SERVER_1, null, BANDWIDTH, STATUS));
		assertNull(subNet.createLink(SUB_LINK_1_TO_2, SUB_SERVER_1, SUB_SERVER_2, BANDWIDTH, STATUS));

		subNet.createServer(SUB_SERVER_1, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		subNet.createServer(SUB_SERVER_2, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		final Link link = subNet.createLink(SUB_LINK_1_TO_2, SUB_SERVER_1, SUB_SERVER_2, BANDWIDTH, STATUS);
		assertEquals(SUB_LINK_1_TO_2, link.getName());
		assertEquals(SUB_SERVER_1, link.getSource().getName());
		assertEquals(SUB_SERVER_2, link.getTarget().getName());
		assertEquals(BANDWIDTH, link.getBandwidth());
		assertEquals(STATUS, link.getStatus());
		assertEquals(subNet, link.getNetwork());
	}

	@Test
	public void testCreateSubstratePath() {
		final Server server1 = subNet.createServer(SUB_SERVER_1, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		final Server server2 = subNet.createServer(SUB_SERVER_2, CPU, MEMORY, STORAGE, DEPTH, STATUS);

		assertTrue(subNet.getPaths().isEmpty());
		final String pathName = "PathId";
		final Path path = subNet.createPath(pathName, SUB_SERVER_1, SUB_SERVER_2, BANDWIDTH, STATUS);
		assertTrue(path instanceof SubstratePath);
		assertEquals(pathName, path.getName());
		assertEquals(BANDWIDTH, path.getBandwidth());
		assertEquals(server1, path.getSourceNode());
		assertEquals(server2, path.getTargetNode());
		assertEquals(subNet, path.getNetwork());
		assertEquals(Arrays.asList(path), subNet.getPaths());
	}

	@Test
	public void testCreateSubstrateServer() {
		final Server server = subNet.createServer(SUB_SERVER_1, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		assertEquals(SUB_SERVER_1, server.getName());
		assertEquals(STATUS, server.getStatus());
		assertEquals(CPU, server.getCpu());
		assertEquals(MEMORY, server.getMemory());
		assertEquals(STORAGE, server.getStorage());
		assertEquals(DEPTH, server.getDepth());
		assertEquals(subNet, server.getNetwork());

		// Error tests
		Server errorServer = subNet.createServer(null, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		assertNull(errorServer);
		errorServer = subNet.createServer("", CPU, MEMORY, STORAGE, DEPTH, STATUS);
		assertNull(errorServer);
	}

	@Test
	public void testCreateSubstrateSwitch() {
		assertTrue(subNet.getNodes().isEmpty());

		final Switch sw = subNet.createSwitch(SUB_SWITCH, DEPTH, STATUS);
		assertTrue(sw instanceof SubstrateSwitch);
		assertEquals(SUB_SWITCH, sw.getName());
		assertEquals(STATUS, sw.getStatus());
		assertEquals(DEPTH, sw.getDepth());
		assertEquals(subNet, sw.getNetwork());
	}

	@Test
	public void testCreateVirtualLink() {
		assertNull(virNet.createLink(VIR_LINK_1_TO_2, null, VIR_SERVER_2, BANDWIDTH, STATUS));
		assertNull(virNet.createLink(VIR_LINK_1_TO_2, VIR_SERVER_1, null, BANDWIDTH, STATUS));
		assertNull(virNet.createLink(VIR_LINK_1_TO_2, VIR_SERVER_1, VIR_SERVER_2, BANDWIDTH, STATUS));

		virNet.createServer(VIR_SERVER_1, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		virNet.createServer(VIR_SERVER_2, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		final Link link = virNet.createLink(VIR_LINK_1_TO_2, VIR_SERVER_1, VIR_SERVER_2, BANDWIDTH, STATUS);
		assertEquals(VIR_LINK_1_TO_2, link.getName());
		assertEquals(VIR_SERVER_1, link.getSource().getName());
		assertEquals(VIR_SERVER_2, link.getTarget().getName());
		assertEquals(BANDWIDTH, link.getBandwidth());
		assertEquals(STATUS, link.getStatus());
		assertEquals(virNet, link.getNetwork());
	}

	@Test
	public void testCreateVirtualProcess() {
		assertTrue(virNet.getProcesses().isEmpty());

		final Server server1 = virNet.createServer(VIR_SERVER_1, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		final boolean hasDatabase = true;
		final String processName = "Process";
		final Process process = virNet.createProcess(processName, hasDatabase, VIR_SERVER_1, STATUS);
		assertTrue(process instanceof VirtualProcess);
		assertEquals(processName, process.getName());
		assertEquals(hasDatabase, process.isHasDatabase());
		assertEquals(server1, process.getServer());
		assertEquals(STATUS, process.getStatus());
	}

	@Test
	public void testCreateVirtualServer() {
		final Server server = virNet.createServer(VIR_SERVER_1, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		assertEquals(VIR_SERVER_1, server.getName());
		assertEquals(STATUS, server.getStatus());
		assertEquals(CPU, server.getCpu());
		assertEquals(MEMORY, server.getMemory());
		assertEquals(STORAGE, server.getStorage());
		assertEquals(DEPTH, server.getDepth());
		assertEquals(virNet, server.getNetwork());

		// Error tests
		Server errorServer = virNet.createServer(null, CPU, MEMORY, STORAGE, DEPTH, STATUS);
		assertNull(errorServer);
		errorServer = virNet.createServer("", CPU, MEMORY, STORAGE, DEPTH, STATUS);
		assertNull(errorServer);
	}

	@Test
	public void testCreateVirtualSwitch() {
		assertTrue(virNet.getNodes().isEmpty());

		final Switch sw = virNet.createSwitch(VIR_SWITCH, DEPTH, STATUS);
		assertTrue(sw instanceof VirtualSwitch);
		assertEquals(VIR_SWITCH, sw.getName());
		assertEquals(STATUS, sw.getStatus());
		assertEquals(DEPTH, sw.getDepth());
		assertEquals(virNet, sw.getNetwork());
	}

}
