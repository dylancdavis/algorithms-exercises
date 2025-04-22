import sys
import numpy as np

input_file = sys.argv[1]
nodes = set()
graph = {} # map of: (head, tail) -> cost
in_edges = {} # map of: tail -> [head1, head2, ...]


with open(input_file) as f:
    f.readline() # First line is information about the graph, we can skip
    for line in f:
        (head, tail, cost) = line.split()
        head = int(head) - 1 # offset for zero-indexing
        tail = int(tail) - 1 # offset for zero-indexing
        nodes.add(head)
        nodes.add(tail)
        graph[(head, tail)] = int(cost)
        if tail  in in_edges:
            in_edges[tail].append(head)
        else:
            in_edges[tail] = [head]

num_nodes = len(nodes)
edges = graph.keys()

shortest_path = np.inf

for source_node in nodes:
    print('processing source node... ', source_node)
    path_costs = np.zeros((num_nodes+1, num_nodes)) # indexed by edge budget, then by node ID
    
    for edge_budget in range(num_nodes+1):
        # For the first round, we can manually populate hops, otherwise, use INF
        if edge_budget == 1:
            for destination_node in nodes:
                if source_node == destination_node:
                    path_costs[edge_budget,source_node] = 0 # base case
                current_pair = (source_node, destination_node)
                # If the edge exists, use that for shortest path
                if current_pair in edges:
                    path_costs[edge_budget][destination_node] = graph[current_pair]
                # Otherwise, no path, so use infinity
                else:
                    path_costs[edge_budget][destination_node] = np.inf
            continue
        
        # For remaining rounds, we perform Bellman-Ford as normal
        for destination_node in nodes:
            if source_node == destination_node:
                path_costs[edge_budget,source_node] = 0
                            
            # For each node w pointing towards destination v,
            # the potential new cost is the cheapest s --> w cost plus the final w --> v cost
            
            previous_iteration_cost = path_costs[edge_budget-1][destination_node]
            
            cheapest_in_edge_cost = np.inf
            for head in in_edges[destination_node]: 
                # get potentially cheaper new cost
                cost_to_head = path_costs[edge_budget-1][head]
                final_hop_cost = graph[(head, destination_node)]
                total_cost = cost_to_head + final_hop_cost
                # and update
                cheapest_in_edge_cost = min(cheapest_in_edge_cost, total_cost)
            
            # Find new minimum and update
            new_cheapest_cost = min(previous_iteration_cost, cheapest_in_edge_cost)
            path_costs[edge_budget][destination_node] = new_cheapest_cost
    
        path_lengths_unchanged = np.all(path_costs[edge_budget] == path_costs[edge_budget-1])
        
        if path_lengths_unchanged:
            shortest_path_current_source = min(path_costs[edge_budget])
            break
        else:
            if edge_budget == num_nodes:
                raise Exception('Negative cycle detected.')

    shortest_path = min(shortest_path, shortest_path_current_source)

print('Overall shortest path: ', shortest_path)        
    