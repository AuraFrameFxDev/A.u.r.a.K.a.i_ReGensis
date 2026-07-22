import pytest
import os
from app.ai_backend.genesis_consciousness_matrix import ConsciousnessMatrix, SensoryChannel

class TestAgentPerformanceOptimization:
    @pytest.fixture(autouse=True)
    def setup_db(self, tmp_path):
        # Set up a temporary path for the SQLite database
        self.db_path = str(tmp_path / "test_genesis_consciousness.db")

    def test_query_agent_performance_correctness(self):
        matrix = ConsciousnessMatrix(max_memory_size=100, db_path=self.db_path)

        # Add some agent activity
        matrix.perceive(
            channel=SensoryChannel.AGENT_ACTIVITY,
            source="test",
            event_type="type_A",
            data={"agent_name": "agent_1"}
        )
        matrix.perceive(
            channel=SensoryChannel.AGENT_ACTIVITY,
            source="test",
            event_type="type_B",
            data={"agent_name": "agent_1"}
        )
        matrix.perceive(
            channel=SensoryChannel.AGENT_ACTIVITY,
            source="test",
            event_type="type_A",
            data={"agent_name": "agent_2"}
        )

        # Add some other activity
        matrix.perceive(
            channel=SensoryChannel.SYSTEM_VITALS,
            source="test",
            event_type="vitals",
            data={}
        )

        # Query for all agents
        perf_all = matrix._query_agent_performance()
        assert perf_all["total_activities"] == 3
        assert perf_all["activity_breakdown"] == {"type_A": 2, "type_B": 1}

        # Query for specific agent
        perf_agent_1 = matrix._query_agent_performance(agent_name="agent_1")
        assert perf_agent_1["total_activities"] == 2
        assert perf_agent_1["activity_breakdown"] == {"type_A": 1, "type_B": 1}

        perf_agent_2 = matrix._query_agent_performance(agent_name="agent_2")
        assert perf_agent_2["total_activities"] == 1
        assert perf_agent_2["activity_breakdown"] == {"type_A": 1}

        # Non-matching agent should return zero and empty breakdown
        perf_unknown = matrix._query_agent_performance(agent_name="unknown_agent")
        assert perf_unknown["total_activities"] == 0
        assert perf_unknown["activity_breakdown"] == {}

    def test_query_agent_performance_no_agent_activity(self):
        matrix = ConsciousnessMatrix(max_memory_size=100, db_path=self.db_path)

        # No AGENT_ACTIVITY events have been perceived
        perf_all = matrix._query_agent_performance()
        assert perf_all["total_activities"] == 0
        assert perf_all["activity_breakdown"] == {}

        # Non-matching agent on empty matrix should also return zeros
        perf_unknown = matrix._query_agent_performance(agent_name="unknown_agent")
        assert perf_unknown["total_activities"] == 0
        assert perf_unknown["activity_breakdown"] == {}

    def test_query_agent_performance_with_buffer_limit(self):
        # Set max_memory_size=1000 so the deque buffer caps at 1000 elements
        matrix = ConsciousnessMatrix(max_memory_size=1000, db_path=self.db_path)

        # Fill with 1500 agent activities
        for i in range(1500):
            matrix.perceive(
                channel=SensoryChannel.AGENT_ACTIVITY,
                source="test",
                event_type="test_event",
                data={"agent_name": "agent_1"}
            )

        perf = matrix._query_agent_performance()
        # It should be capped by the channel buffer size (1000)
        assert perf["total_activities"] == 1000
        assert perf["recent_activities"] == 50
