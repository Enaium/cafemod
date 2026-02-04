// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'rpc.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Rpc _$RpcFromJson(Map<String, dynamic> json) => Rpc(
  type: $enumDecode(_$RpcTypeEnumMap, json['type']),
  method: json['method'] as String,
  params: json['params'] as List<dynamic>?,
  result: json['result'] as String?,
);

Map<String, dynamic> _$RpcToJson(Rpc instance) => <String, dynamic>{
  'type': _$RpcTypeEnumMap[instance.type]!,
  'method': instance.method,
  'params': instance.params,
  'result': instance.result,
};

const _$RpcTypeEnumMap = {
  RpcType.REQUEST: 'REQUEST',
  RpcType.RESPONSE: 'RESPONSE',
};
