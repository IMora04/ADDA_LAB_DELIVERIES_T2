Minimize

1 delivered_0_0 + 50 delivered_0_1 + 10 delivered_0_2 + 15 delivered_0_3 + 10 delivered_0_4 + 4 delivered_1_0 + 3 delivered_1_1 + 3 delivered_1_2 + 2 delivered_1_3 + 1 delivered_1_4


Subject To

a0: delivered_0_0 + delivered_1_0 >= 6
a1: delivered_0_1 + delivered_1_1 >= 3
a2: delivered_0_2 + delivered_1_2 >= 8
a3: delivered_0_3 + delivered_1_3 >= 10
a4: delivered_0_4 + delivered_1_4 >= 5
b0: delivered_0_0 + delivered_0_1 + delivered_0_2 + delivered_0_3 + delivered_0_4 <= 5
b1: delivered_1_0 + delivered_1_1 + delivered_1_2 + delivered_1_3 + delivered_1_4 <= 100





General

 delivered_0_0 delivered_0_1 delivered_0_2 delivered_0_3 delivered_0_4 delivered_1_0 delivered_1_1 delivered_1_2 delivered_1_3 delivered_1_4



End
