import sys

input_file = sys.argv[1]

tree_nodes = set()
untouched_nodes = set()

total_cost = 0

graph = {}


with open(input_file) as f:
    for line in f:
        (node1, node2, cost) = line.split()
        node1 = int(node1)
        node2 = int(node2)
        untouched_nodes.add(node1)
        untouched_nodes.add(node2)
        graph[(int(node1), int(node2))] = int(cost)

print(f"Size of graph: {len(untouched_nodes)}")

# Choose first node randomly
tree_nodes.add(untouched_nodes.pop())
print(f"Size of graph after popping: {len(untouched_nodes)}")

while len(untouched_nodes) > 0:
    # print current untouched nodes
    # print(f"Current untouched nodes (loop start): {untouched_nodes}")
    # get all crossing edges
    crossing_edges = []
    for nodes, cost in graph.items():
        (node1, node2) = nodes
        if node1 in tree_nodes and node2 in tree_nodes:
            continue
        if node1 in untouched_nodes and node2 in untouched_nodes:
            continue
        crossing_edges.append((node1, node2, cost))

    # extract least cost edge
    cheapest_edge = min(crossing_edges, key=lambda e: e[2])
    (node1, node2, cost) = cheapest_edge

    # Check that is crossing edge
    if node1 in tree_nodes and node2 in tree_nodes:
        raise Exception()
    if node1 in untouched_nodes and node2 in untouched_nodes:
        raise Exception()

    # Case: node 2 is new vertex
    if node1 in tree_nodes:
        untouched_nodes.remove(node2)
        tree_nodes.add(node2)
    # Case: node 1 is new vertex
    else:
        untouched_nodes.remove(node1)
        tree_nodes.add(node1)
    total_cost += cost

    # print(f"Current untouched nodes (loop end): {untouched_nodes}")


print(total_cost)
