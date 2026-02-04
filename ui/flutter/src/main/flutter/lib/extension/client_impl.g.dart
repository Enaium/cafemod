// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'client_impl.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ZipEntry _$ZipEntryFromJson(Map<String, dynamic> json) => ZipEntry(
  name: json['name'] as String,
  path: json['path'] as String,
  type: $enumDecode(_$ZipEntryTypeEnumMap, json['type']),
  parent: json['parent'] == null
      ? null
      : ZipEntry.fromJson(json['parent'] as Map<String, dynamic>),
  children: (json['children'] as List<dynamic>?)
      ?.map((e) => ZipEntry.fromJson(e as Map<String, dynamic>))
      .toList(),
);

Map<String, dynamic> _$ZipEntryToJson(ZipEntry instance) => <String, dynamic>{
  'name': instance.name,
  'path': instance.path,
  'type': _$ZipEntryTypeEnumMap[instance.type]!,
  'parent': instance.parent,
  'children': instance.children,
};

const _$ZipEntryTypeEnumMap = {
  ZipEntryType.FILE: 'FILE',
  ZipEntryType.DIRECTORY: 'DIRECTORY',
};

Field _$FieldFromJson(Map<String, dynamic> json) => Field(
  json['name'] as String,
  json['desc'] as String,
  (json['access'] as num).toInt(),
);

Map<String, dynamic> _$FieldToJson(Field instance) => <String, dynamic>{
  'name': instance.name,
  'desc': instance.desc,
  'access': instance.access,
};

Method _$MethodFromJson(Map<String, dynamic> json) => Method(
  json['name'] as String,
  json['desc'] as String,
  (json['access'] as num).toInt(),
);

Map<String, dynamic> _$MethodToJson(Method instance) => <String, dynamic>{
  'name': instance.name,
  'desc': instance.desc,
  'access': instance.access,
};

InstructionNode _$InstructionNodeFromJson(Map<String, dynamic> json) =>
    InstructionNode(
      (json['type'] as num).toInt(),
      (json['opcode'] as num).toInt(),
    );

Map<String, dynamic> _$InstructionNodeToJson(InstructionNode instance) =>
    <String, dynamic>{'type': instance.type, 'opcode': instance.opcode};

FieldInstructionNode _$FieldInstructionNodeFromJson(
  Map<String, dynamic> json,
) => FieldInstructionNode(
  (json['type'] as num).toInt(),
  (json['opcode'] as num).toInt(),
  json['owner'] as String,
  json['name'] as String,
  json['descriptor'] as String,
);

Map<String, dynamic> _$FieldInstructionNodeToJson(
  FieldInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'owner': instance.owner,
  'name': instance.name,
  'descriptor': instance.descriptor,
};

FrameInstructionNode _$FrameInstructionNodeFromJson(
  Map<String, dynamic> json,
) => FrameInstructionNode(
  (json['type'] as num).toInt(),
  (json['opcode'] as num).toInt(),
  (json['frame'] as num).toInt(),
  json['local'] as List<dynamic>,
  json['stack'] as List<dynamic>,
);

Map<String, dynamic> _$FrameInstructionNodeToJson(
  FrameInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'frame': instance.frame,
  'local': instance.local,
  'stack': instance.stack,
};

IincInstructionNode _$IincInstructionNodeFromJson(Map<String, dynamic> json) =>
    IincInstructionNode(
      (json['type'] as num).toInt(),
      (json['opcode'] as num).toInt(),
      (json['varIndex'] as num).toInt(),
      (json['increment'] as num).toInt(),
    );

Map<String, dynamic> _$IincInstructionNodeToJson(
  IincInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'varIndex': instance.varIndex,
  'increment': instance.increment,
};

InsnInstructionNode _$InsnInstructionNodeFromJson(Map<String, dynamic> json) =>
    InsnInstructionNode(
      (json['type'] as num).toInt(),
      (json['opcode'] as num).toInt(),
    );

Map<String, dynamic> _$InsnInstructionNodeToJson(
  InsnInstructionNode instance,
) => <String, dynamic>{'type': instance.type, 'opcode': instance.opcode};

IntInstructionNode _$IntInstructionNodeFromJson(Map<String, dynamic> json) =>
    IntInstructionNode(
      (json['type'] as num).toInt(),
      (json['opcode'] as num).toInt(),
      (json['operand'] as num).toInt(),
    );

Map<String, dynamic> _$IntInstructionNodeToJson(IntInstructionNode instance) =>
    <String, dynamic>{
      'type': instance.type,
      'opcode': instance.opcode,
      'operand': instance.operand,
    };

InvokeDynamicInstructionNode _$InvokeDynamicInstructionNodeFromJson(
  Map<String, dynamic> json,
) => InvokeDynamicInstructionNode(
  (json['type'] as num).toInt(),
  (json['opcode'] as num).toInt(),
  json['name'] as String,
  json['desc'] as String,
);

Map<String, dynamic> _$InvokeDynamicInstructionNodeToJson(
  InvokeDynamicInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'name': instance.name,
  'desc': instance.desc,
};

JumpInstructionNode _$JumpInstructionNodeFromJson(Map<String, dynamic> json) =>
    JumpInstructionNode(
      (json['type'] as num).toInt(),
      (json['opcode'] as num).toInt(),
      (json['labelIndex'] as num).toInt(),
    );

Map<String, dynamic> _$JumpInstructionNodeToJson(
  JumpInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'labelIndex': instance.labelIndex,
};

LabelInstructionNode _$LabelInstructionNodeFromJson(
  Map<String, dynamic> json,
) => LabelInstructionNode(
  (json['type'] as num).toInt(),
  (json['opcode'] as num).toInt(),
  (json['labelIndex'] as num).toInt(),
);

Map<String, dynamic> _$LabelInstructionNodeToJson(
  LabelInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'labelIndex': instance.labelIndex,
};

LdcInstructionNode _$LdcInstructionNodeFromJson(Map<String, dynamic> json) =>
    LdcInstructionNode(
      (json['type'] as num).toInt(),
      (json['opcode'] as num).toInt(),
      json['cst'],
    );

Map<String, dynamic> _$LdcInstructionNodeToJson(LdcInstructionNode instance) =>
    <String, dynamic>{
      'type': instance.type,
      'opcode': instance.opcode,
      'cst': instance.cst,
    };

LineNumberInstructionNode _$LineNumberInstructionNodeFromJson(
  Map<String, dynamic> json,
) => LineNumberInstructionNode(
  (json['type'] as num).toInt(),
  (json['opcode'] as num).toInt(),
  (json['line'] as num).toInt(),
  (json['startLabelIndex'] as num).toInt(),
);

Map<String, dynamic> _$LineNumberInstructionNodeToJson(
  LineNumberInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'line': instance.line,
  'startLabelIndex': instance.startLabelIndex,
};

LookupSwitchInstructionNode _$LookupSwitchInstructionNodeFromJson(
  Map<String, dynamic> json,
) => LookupSwitchInstructionNode(
  (json['type'] as num).toInt(),
  (json['opcode'] as num).toInt(),
  (json['dfltLabelIndex'] as num).toInt(),
);

Map<String, dynamic> _$LookupSwitchInstructionNodeToJson(
  LookupSwitchInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'dfltLabelIndex': instance.dfltLabelIndex,
};

MethodInstructionNode _$MethodInstructionNodeFromJson(
  Map<String, dynamic> json,
) => MethodInstructionNode(
  (json['type'] as num).toInt(),
  (json['opcode'] as num).toInt(),
  json['owner'] as String,
  json['name'] as String,
  json['desc'] as String,
);

Map<String, dynamic> _$MethodInstructionNodeToJson(
  MethodInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'owner': instance.owner,
  'name': instance.name,
  'desc': instance.desc,
};

MultiANewArrayInstructionNode _$MultiANewArrayInstructionNodeFromJson(
  Map<String, dynamic> json,
) => MultiANewArrayInstructionNode(
  (json['type'] as num).toInt(),
  (json['opcode'] as num).toInt(),
  json['desc'] as String,
  (json['dims'] as num).toInt(),
);

Map<String, dynamic> _$MultiANewArrayInstructionNodeToJson(
  MultiANewArrayInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'desc': instance.desc,
  'dims': instance.dims,
};

TableSwitchInstructionNode _$TableSwitchInstructionNodeFromJson(
  Map<String, dynamic> json,
) => TableSwitchInstructionNode(
  (json['type'] as num).toInt(),
  (json['opcode'] as num).toInt(),
  (json['min'] as num).toInt(),
  (json['max'] as num).toInt(),
  (json['dfltLabelIndex'] as num).toInt(),
  (json['labelIndexes'] as List<dynamic>)
      .map((e) => (e as num).toInt())
      .toList(),
);

Map<String, dynamic> _$TableSwitchInstructionNodeToJson(
  TableSwitchInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'min': instance.min,
  'max': instance.max,
  'dfltLabelIndex': instance.dfltLabelIndex,
  'labelIndexes': instance.labelIndexes,
};

TypeInstructionNode _$TypeInstructionNodeFromJson(Map<String, dynamic> json) =>
    TypeInstructionNode(
      (json['type'] as num).toInt(),
      (json['opcode'] as num).toInt(),
      json['desc'] as String,
    );

Map<String, dynamic> _$TypeInstructionNodeToJson(
  TypeInstructionNode instance,
) => <String, dynamic>{
  'type': instance.type,
  'opcode': instance.opcode,
  'desc': instance.desc,
};

VarInstructionNode _$VarInstructionNodeFromJson(Map<String, dynamic> json) =>
    VarInstructionNode(
      (json['type'] as num).toInt(),
      (json['opcode'] as num).toInt(),
      (json['varIndex'] as num).toInt(),
    );

Map<String, dynamic> _$VarInstructionNodeToJson(VarInstructionNode instance) =>
    <String, dynamic>{
      'type': instance.type,
      'opcode': instance.opcode,
      'varIndex': instance.varIndex,
    };
