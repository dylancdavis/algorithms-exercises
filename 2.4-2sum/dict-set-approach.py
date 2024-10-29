import sys
input_file = sys.argv[1]

# both bounds will be treated as inclusive
upper_bound = 10000
lower_bound = -upper_bound

# Parse input
with open(input_file) as f:
    nums = [int(line) for line in f]

num_set = set(nums) # 1 million
nums_to_find = set(range(lower_bound, upper_bound + 1)) # 20k, but should decrease as nums are found
total_nums = len(nums_to_find) # for later comparison

for num in num_set:  
    found_nums = set() # can't modify set while iterating, so store found nums separately
    
    # Check for solutions for each target
    for target in nums_to_find:
        difference = target - num
        # Solutions must be distinct i.e. (target - num) !== num
        if not difference == num and difference in num_set:
            found_nums.add(target)
    
    # Remove any solutions from the set to check
    for target in found_nums:
        nums_to_find.remove(target)

nums_found = total_nums - len(nums_to_find)
print(f"Nums found: {nums_found}")
