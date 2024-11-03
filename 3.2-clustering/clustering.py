import sys

input_file = sys.argv[1]

NUM_NODES = 500
CLUSTER_NUMBER = 4

# Parse input
with open(input_file) as f:
    edges = []
    leader_nodes = {} # (node --> leader_node)
    leader_sizes = {} # (leader_node --> number of nodes)
    for line in f:
        (node1, node2, cost) = line.split()
        node1 = int(node1)
        node2 = int(node2)
        cost = int(cost)
        
        edges.append((node1, node2, cost))
        leader_nodes[node1] = node1
        leader_sizes[node1] = 1
        leader_nodes[node2] = node2
        leader_sizes[node2] = 1

# Sort edges by cheapest first
sorted_edges = sorted(edges, key=lambda entry: entry[2]) # entry[2] is cost

# print(sorted_edges[10000:10200])

for (node1, node2, cost) in sorted_edges:
    
    leader1 = leader_nodes[node1]
    leader2 = leader_nodes[node2]
    
    # Same partition, skip
    if leader1 == leader2:
        continue

    # Clusters met, end loop
    if len(leader_sizes) == CLUSTER_NUMBER:
        final_maps = {}
        print('Leader sizes:', leader_sizes)
        for key in leader_sizes:
            final_maps[key] = []
        for key in leader_nodes:
            leader = leader_nodes[key]
            final_maps[leader].append(key)
        
        print('Final map:', final_maps)
        print('Next cheapest edge:', (node1, node2, cost))
        break

    # Determine which leader to convert to other
    if leader_sizes[leader1] < leader_sizes[leader2]:
        smaller_leader = leader1
        larger_leader = leader2
    else:
        smaller_leader = leader2
        larger_leader = leader1
    
    # Update entries
    # still linear scan so not efficient
    for entry in leader_nodes:
        if leader_nodes[entry] == smaller_leader:
            leader_nodes[entry] = larger_leader

    # and update sizes.
    leader_sizes[larger_leader] += leader_sizes.pop(smaller_leader)
    
    # Else, different partitions. Perform Union
    # must update all nodes to have the larger (or smaller) leader node
    
    # for efficient updates, we only update the smaller group
    
    new_leader = max(leader1, leader2)
    
    # hm. or do we just update the references until we reach a self-referential node? That might make more sense.