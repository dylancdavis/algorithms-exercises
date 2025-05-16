import sys
import math
import numpy as np

if len(sys.argv) != 2:
    print("Usage: python tsp.py <data_file>")
    sys.exit(1)

data_file = sys.argv[1]

cities = []

# Read the file
with open(data_file, 'r') as file:
    for line in file:
        (_,x,y) = line.split(' ')
        cities.append((float(x),float(y)))

num_cities = len(cities)

visited = np.ndarray((num_cities))
visited.fill(False)

tour_length = 0

current_index = 0
visited[current_index] = True

# Visit closest cities, one after another
while not np.all(visited):
    print('Visited: ', np.count_nonzero(visited))
    nearest_neighbor = None
    shortest_distance = np.inf
    current_city = cities[current_index]
    
    for i in range(num_cities):
        if visited[i]:
            continue
        (x,y) = current_city
        (z,w) = cities[i]
        squared_distance = (x-z)**2 + (y-w)**2
        if squared_distance < shortest_distance:
            shortest_distance = squared_distance
            nearest_neighbor = i
    
    tour_length += math.sqrt(shortest_distance)
    visited[nearest_neighbor] = True
    current_index = nearest_neighbor

# Add final distance to close loop
(x,y) = cities[current_index] # final city
(z,w) = cities[0] # first city
final_distance = math.sqrt((x-z)**2 + (y-w)**2)
tour_length += final_distance

print('Final distance: ', tour_length)