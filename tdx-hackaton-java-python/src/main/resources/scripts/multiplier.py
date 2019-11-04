from py4j.java_collections import SetConverter, MapConverter, ListConverter

result = list(map(lambda x: x*2, numbers))

# Required conversions
result = ListConverter().convert(result, gateway._gateway_client)