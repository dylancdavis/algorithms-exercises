import sys

input_file = sys.argv[1]

with open(input_file) as f:
    items = []
    dataset_info = f.readline().split()
    (total_size, num_items) = map(int, dataset_info)

    print("Total size: ", total_size)
    print("Number of initial items: ", num_items)

    for line in f:
        (value, size) = map(int, line.split())
        # if size > total_size:
        #     continue
        items.append((value, size))

sys.setrecursionlimit(num_items+3) # at least 3 extra calls for existing call stack
solutions = {}

def get_solution(index, max_size):
    if (index, max_size) in solutions:
        return solutions[(index, max_size)]
    
    if index < 0 or max_size <= 0:
        return 0
    
    
    (value, size) = items[index]
    

    
    solution_if_excluded = get_solution(index-1, max_size)
    if (size > max_size):
        return solution_if_excluded

    solution_if_included = get_solution(index-1, max_size - size) + value
    
    solution = max(solution_if_included, solution_if_excluded)
    solutions[(index, max_size)] = solution
    return solution
        
print(get_solution(num_items-1, total_size))