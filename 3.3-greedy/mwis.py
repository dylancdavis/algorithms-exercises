
import sys

# Optimal solution for all indices
def get_mwis(nums):
    optimal_values = [0] * len(nums)
    for (index, num) in enumerate(nums):
        if index == 0:
            optimal_values[0] = num
            continue
        if index == 1:
            optimal_values[1] = max(optimal_values[0], num)
            continue
        value_with_num = optimal_values[index - 2] + num
        value_without = optimal_values[index - 1]
        optimal_values[index] = max(value_with_num, value_without)
    return optimal_values
    
# Reconstruction function
def get_chosen_indices(optimal_values):
    chosen_indices = set()
    index = len(optimal_values) - 1
    
    while index >= 0:
        if index == 0:
            if 1 not in chosen_indices:
                chosen_indices.add(index)
        else:
            value_at_index = optimal_values[index]
            value_at_prev = optimal_values[index-1]
            if not value_at_index == value_at_prev:
                chosen_indices.add(index)
                index -= 1 # skip next index as it won't have been chosen
        index -= 1
    return chosen_indices


input_file = sys.argv[1]

with open(input_file) as f:
    nums = []
    for line in f:
        nums.append(int(line))

# NOTE: 1-indexed, not zero!
indices_of_interest = [1, 2, 3, 4, 17, 117, 517, 997]

solutions = get_mwis(nums)
print(solutions)
chosen_indices = get_chosen_indices(solutions)
print(chosen_indices)
got_chosen = list(map(lambda n: (n-1) in chosen_indices, indices_of_interest))
print(got_chosen)