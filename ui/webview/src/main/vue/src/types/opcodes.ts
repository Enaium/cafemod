export const ACC_PUBLIC = 0x0001
export const ACC_PRIVATE = 0x0002
export const ACC_PROTECTED = 0x0004
export const ACC_STATIC = 0x0008
export const ACC_FINAL = 0x0010

export const NOP = 0
export const ACONST_NULL = 1
export const ICONST_M1 = 2
export const ICONST_0 = 3
export const ICONST_1 = 4
export const ICONST_2 = 5
export const ICONST_3 = 6
export const ICONST_4 = 7
export const ICONST_5 = 8
export const LCONST_0 = 9
export const LCONST_1 = 10
export const FCONST_0 = 11
export const FCONST_1 = 12
export const FCONST_2 = 13
export const DCONST_0 = 14
export const DCONST_1 = 15
export const BIPUSH = 16
export const SIPUSH = 17
export const LDC = 18
export const ILOAD = 21
export const LLOAD = 22
export const FLOAD = 23
export const DLOAD = 24
export const ALOAD = 25
export const IALOAD = 46
export const LALOAD = 47
export const FALOAD = 48
export const DALOAD = 49
export const AALOAD = 50
export const BALOAD = 51
export const CALOAD = 52
export const SALOAD = 53
export const ISTORE = 54
export const LSTORE = 55
export const FSTORE = 56
export const DSTORE = 57
export const ASTORE = 58
export const IASTORE = 79
export const LASTORE = 80
export const FASTORE = 81
export const DASTORE = 82
export const AASTORE = 83
export const BASTORE = 84
export const CASTORE = 85
export const SASTORE = 86
export const POP = 87
export const POP2 = 88
export const DUP = 89
export const DUP_X1 = 90
export const DUP_X2 = 91
export const DUP2 = 92
export const DUP2_X1 = 93
export const DUP2_X2 = 94
export const SWAP = 95
export const IADD = 96
export const LADD = 97
export const FADD = 98
export const DADD = 99
export const ISUB = 100
export const LSUB = 101
export const FSUB = 102
export const DSUB = 103
export const IMUL = 104
export const LMUL = 105
export const FMUL = 106
export const DMUL = 107
export const IDIV = 108
export const LDIV = 109
export const FDIV = 110
export const DDIV = 111
export const IREM = 112
export const LREM = 113
export const FREM = 114
export const DREM = 115
export const INEG = 116
export const LNEG = 117
export const FNEG = 118
export const DNEG = 119
export const ISHL = 120
export const LSHL = 121
export const ISHR = 122
export const LSHR = 123
export const IUSHR = 124
export const LUSHR = 125
export const IAND = 126
export const LAND = 127
export const IOR = 128
export const LOR = 129
export const IXOR = 130
export const LXOR = 131
export const IINC = 132
export const I2L = 133
export const I2F = 134
export const I2D = 135
export const L2I = 136
export const L2F = 137
export const L2D = 138
export const F2I = 139
export const F2L = 140
export const F2D = 141
export const D2I = 142
export const D2L = 143
export const D2F = 144
export const I2B = 145
export const I2C = 146
export const I2S = 147
export const LCMP = 148
export const FCMPL = 149
export const FCMPG = 150
export const DCMPL = 151
export const DCMPG = 152
export const IFEQ = 153
export const IFNE = 154
export const IFLT = 155
export const IFGE = 156
export const IFGT = 157
export const IFLE = 158
export const IF_ICMPEQ = 159
export const IF_ICMPNE = 160
export const IF_ICMPLT = 161
export const IF_ICMPGE = 162
export const IF_ICMPGT = 163
export const IF_ICMPLE = 164
export const IF_ACMPEQ = 165
export const IF_ACMPNE = 166
export const GOTO = 167
export const JSR = 168
export const RET = 169
export const TABLESWITCH = 170
export const LOOKUPSWITCH = 171
export const IRETURN = 172
export const LRETURN = 173
export const FRETURN = 174
export const DRETURN = 175
export const ARETURN = 176
export const RETURN = 177
export const GETSTATIC = 178
export const PUTSTATIC = 179
export const GETFIELD = 180
export const PUTFIELD = 181
export const INVOKEVIRTUAL = 182
export const INVOKESPECIAL = 183
export const INVOKESTATIC = 184
export const INVOKEINTERFACE = 185
export const INVOKEDYNAMIC = 186
export const NEW = 187
export const NEWARRAY = 188
export const ANEWARRAY = 189
export const ARRAYLENGTH = 190
export const ATHROW = 191
export const CHECKCAST = 192
export const INSTANCEOF = 193
export const MONITORENTER = 194
export const MONITOREXIT = 195
export const MULTIANEWARRAY = 197
export const IFNULL = 198
export const IFNONNULL = 199

export const opcodes = [
  'NOP', // 0 (0x0)
  'ACONST_NULL', // 1 (0x1)
  'ICONST_M1', // 2 (0x2)
  'ICONST_0', // 3 (0x3)
  'ICONST_1', // 4 (0x4)
  'ICONST_2', // 5 (0x5)
  'ICONST_3', // 6 (0x6)
  'ICONST_4', // 7 (0x7)
  'ICONST_5', // 8 (0x8)
  'LCONST_0', // 9 (0x9)
  'LCONST_1', // 10 (0xa)
  'FCONST_0', // 11 (0xb)
  'FCONST_1', // 12 (0xc)
  'FCONST_2', // 13 (0xd)
  'DCONST_0', // 14 (0xe)
  'DCONST_1', // 15 (0xf)
  'BIPUSH', // 16 (0x10)
  'SIPUSH', // 17 (0x11)
  'LDC', // 18 (0x12)
  'LDC_W', // 19 (0x13)
  'LDC2_W', // 20 (0x14)
  'ILOAD', // 21 (0x15)
  'LLOAD', // 22 (0x16)
  'FLOAD', // 23 (0x17)
  'DLOAD', // 24 (0x18)
  'ALOAD', // 25 (0x19)
  'ILOAD_0', // 26 (0x1a)
  'ILOAD_1', // 27 (0x1b)
  'ILOAD_2', // 28 (0x1c)
  'ILOAD_3', // 29 (0x1d)
  'LLOAD_0', // 30 (0x1e)
  'LLOAD_1', // 31 (0x1f)
  'LLOAD_2', // 32 (0x20)
  'LLOAD_3', // 33 (0x21)
  'FLOAD_0', // 34 (0x22)
  'FLOAD_1', // 35 (0x23)
  'FLOAD_2', // 36 (0x24)
  'FLOAD_3', // 37 (0x25)
  'DLOAD_0', // 38 (0x26)
  'DLOAD_1', // 39 (0x27)
  'DLOAD_2', // 40 (0x28)
  'DLOAD_3', // 41 (0x29)
  'ALOAD_0', // 42 (0x2a)
  'ALOAD_1', // 43 (0x2b)
  'ALOAD_2', // 44 (0x2c)
  'ALOAD_3', // 45 (0x2d)
  'IALOAD', // 46 (0x2e)
  'LALOAD', // 47 (0x2f)
  'FALOAD', // 48 (0x30)
  'DALOAD', // 49 (0x31)
  'AALOAD', // 50 (0x32)
  'BALOAD', // 51 (0x33)
  'CALOAD', // 52 (0x34)
  'SALOAD', // 53 (0x35)
  'ISTORE', // 54 (0x36)
  'LSTORE', // 55 (0x37)
  'FSTORE', // 56 (0x38)
  'DSTORE', // 57 (0x39)
  'ASTORE', // 58 (0x3a)
  'ISTORE_0', // 59 (0x3b)
  'ISTORE_1', // 60 (0x3c)
  'ISTORE_2', // 61 (0x3d)
  'ISTORE_3', // 62 (0x3e)
  'LSTORE_0', // 63 (0x3f)
  'LSTORE_1', // 64 (0x40)
  'LSTORE_2', // 65 (0x41)
  'LSTORE_3', // 66 (0x42)
  'FSTORE_0', // 67 (0x43)
  'FSTORE_1', // 68 (0x44)
  'FSTORE_2', // 69 (0x45)
  'FSTORE_3', // 70 (0x46)
  'DSTORE_0', // 71 (0x47)
  'DSTORE_1', // 72 (0x48)
  'DSTORE_2', // 73 (0x49)
  'DSTORE_3', // 74 (0x4a)
  'ASTORE_0', // 75 (0x4b)
  'ASTORE_1', // 76 (0x4c)
  'ASTORE_2', // 77 (0x4d)
  'ASTORE_3', // 78 (0x4e)
  'IASTORE', // 79 (0x4f)
  'LASTORE', // 80 (0x50)
  'FASTORE', // 81 (0x51)
  'DASTORE', // 82 (0x52)
  'AASTORE', // 83 (0x53)
  'BASTORE', // 84 (0x54)
  'CASTORE', // 85 (0x55)
  'SASTORE', // 86 (0x56)
  'POP', // 87 (0x57)
  'POP2', // 88 (0x58)
  'DUP', // 89 (0x59)
  'DUP_X1', // 90 (0x5a)
  'DUP_X2', // 91 (0x5b)
  'DUP2', // 92 (0x5c)
  'DUP2_X1', // 93 (0x5d)
  'DUP2_X2', // 94 (0x5e)
  'SWAP', // 95 (0x5f)
  'IADD', // 96 (0x60)
  'LADD', // 97 (0x61)
  'FADD', // 98 (0x62)
  'DADD', // 99 (0x63)
  'ISUB', // 100 (0x64)
  'LSUB', // 101 (0x65)
  'FSUB', // 102 (0x66)
  'DSUB', // 103 (0x67)
  'IMUL', // 104 (0x68)
  'LMUL', // 105 (0x69)
  'FMUL', // 106 (0x6a)
  'DMUL', // 107 (0x6b)
  'IDIV', // 108 (0x6c)
  'LDIV', // 109 (0x6d)
  'FDIV', // 110 (0x6e)
  'DDIV', // 111 (0x6f)
  'IREM', // 112 (0x70)
  'LREM', // 113 (0x71)
  'FREM', // 114 (0x72)
  'DREM', // 115 (0x73)
  'INEG', // 116 (0x74)
  'LNEG', // 117 (0x75)
  'FNEG', // 118 (0x76)
  'DNEG', // 119 (0x77)
  'ISHL', // 120 (0x78)
  'LSHL', // 121 (0x79)
  'ISHR', // 122 (0x7a)
  'LSHR', // 123 (0x7b)
  'IUSHR', // 124 (0x7c)
  'LUSHR', // 125 (0x7d)
  'IAND', // 126 (0x7e)
  'LAND', // 127 (0x7f)
  'IOR', // 128 (0x80)
  'LOR', // 129 (0x81)
  'IXOR', // 130 (0x82)
  'LXOR', // 131 (0x83)
  'IINC', // 132 (0x84)
  'I2L', // 133 (0x85)
  'I2F', // 134 (0x86)
  'I2D', // 135 (0x87)
  'L2I', // 136 (0x88)
  'L2F', // 137 (0x89)
  'L2D', // 138 (0x8a)
  'F2I', // 139 (0x8b)
  'F2L', // 140 (0x8c)
  'F2D', // 141 (0x8d)
  'D2I', // 142 (0x8e)
  'D2L', // 143 (0x8f)
  'D2F', // 144 (0x90)
  'I2B', // 145 (0x91)
  'I2C', // 146 (0x92)
  'I2S', // 147 (0x93)
  'LCMP', // 148 (0x94)
  'FCMPL', // 149 (0x95)
  'FCMPG', // 150 (0x96)
  'DCMPL', // 151 (0x97)
  'DCMPG', // 152 (0x98)
  'IFEQ', // 153 (0x99)
  'IFNE', // 154 (0x9a)
  'IFLT', // 155 (0x9b)
  'IFGE', // 156 (0x9c)
  'IFGT', // 157 (0x9d)
  'IFLE', // 158 (0x9e)
  'IF_ICMPEQ', // 159 (0x9f)
  'IF_ICMPNE', // 160 (0xa0)
  'IF_ICMPLT', // 161 (0xa1)
  'IF_ICMPGE', // 162 (0xa2)
  'IF_ICMPGT', // 163 (0xa3)
  'IF_ICMPLE', // 164 (0xa4)
  'IF_ACMPEQ', // 165 (0xa5)
  'IF_ACMPNE', // 166 (0xa6)
  'GOTO', // 167 (0xa7)
  'JSR', // 168 (0xa8)
  'RET', // 169 (0xa9)
  'TABLESWITCH', // 170 (0xaa)
  'LOOKUPSWITCH', // 171 (0xab)
  'IRETURN', // 172 (0xac)
  'LRETURN', // 173 (0xad)
  'FRETURN', // 174 (0xae)
  'DRETURN', // 175 (0xaf)
  'ARETURN', // 176 (0xb0)
  'RETURN', // 177 (0xb1)
  'GETSTATIC', // 178 (0xb2)
  'PUTSTATIC', // 179 (0xb3)
  'GETFIELD', // 180 (0xb4)
  'PUTFIELD', // 181 (0xb5)
  'INVOKEVIRTUAL', // 182 (0xb6)
  'INVOKESPECIAL', // 183 (0xb7)
  'INVOKESTATIC', // 184 (0xb8)
  'INVOKEINTERFACE', // 185 (0xb9)
  'INVOKEDYNAMIC', // 186 (0xba)
  'NEW', // 187 (0xbb)
  'NEWARRAY', // 188 (0xbc)
  'ANEWARRAY', // 189 (0xbd)
  'ARRAYLENGTH', // 190 (0xbe)
  'ATHROW', // 191 (0xbf)
  'CHECKCAST', // 192 (0xc0)
  'INSTANCEOF', // 193 (0xc1)
  'MONITORENTER', // 194 (0xc2)
  'MONITOREXIT', // 195 (0xc3)
  'WIDE', // 196 (0xc4)
  'MULTIANEWARRAY', // 197 (0xc5)
  'IFNULL', // 198 (0xc6)
  'IFNONNULL', // 199 (0xc7)
]

export const INSN = 0
export const INT_INSN = 1
export const VAR_INSN = 2
export const TYPE_INSN = 3
export const FIELD_INSN = 4
export const METHOD_INSN = 5
export const INVOKE_DYNAMIC_INSN = 6
export const JUMP_INSN = 7
export const LABEL = 8
export const LDC_INSN = 9
export const IINC_INSN = 10
export const TABLESWITCH_INSN = 11
export const LOOKUPSWITCH_INSN = 12
export const MULTIANEWARRAY_INSN = 13
export const FRAME = 14
export const LINE = 15

export const F_NEW = -1
export const F_FULL = 0
export const F_APPEND = 1
export const F_CHOP = 2
export const F_SAME = 3
export const F_SAME1 = 4
