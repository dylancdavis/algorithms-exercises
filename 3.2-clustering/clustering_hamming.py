import sys

input_file = sys.argv[1]

NUM_BITS = 24

one_bit_masks = [2 ** n for n in range(0, NUM_BITS)]
two_bit_masks = set()

# Two-bit masks automatically includes one-bit masks
for n in one_bit_masks:
    for k in one_bit_masks:
        two_bit_masks.add(n | k)

# Parse input
with open(input_file) as f:
    nums = set()
    for line in f:
        nums.add(int(line.replace(' ', ''), 2))

partitions = {} # node --> partition ID (just the value of a node inside)
partition_sizes = {} # partition ID --> # nodes in that partition
reverse_partitions = {} # partition ID -> nodes tracked in that set

# start with n partitions
for index, num in enumerate(nums):
    partitions[num] = num
    partition_sizes[num] = 1
    reverse_partitions[num] = [num]

# Main union function.
# Join partitions, or ignore if already unioned
def union(n1, n2):
    partition1 = partitions[n1]
    partition2 = partitions[n2]
    
    # No union when in same partition
    if partition1 == partition2:
        return

    # Determine which leader to convert to other
    if partition_sizes[partition1] < partition_sizes[partition2]:
        smaller_partition = partition1
        larger_partition = partition2
    else:
        smaller_partition = partition2
        larger_partition = partition1
    
    # Update entries in normal and reverse mappings
    transfer_nums = reverse_partitions.pop(smaller_partition)
    for num in transfer_nums:
        partitions[num] = larger_partition
    reverse_partitions[larger_partition] += transfer_nums

    # and update sizes.
    partition_sizes[larger_partition] += partition_sizes.pop(smaller_partition)

# Union all 1-distance and 2-distance numbers
# We generate a list of all possible numbers up to two bits difference
# Then if they exist, they must be in the same partition
for index, n in enumerate(nums):
    nums_to_check = [n ^ mask for mask in two_bit_masks]
    for k in nums_to_check:
        if k in nums:
            union(n, k)

print(len(partition_sizes))