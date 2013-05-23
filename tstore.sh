#!/bin/sh

# Note: single-node config is fine, since client bootstraps to find
# all configs there may be assuming 9090 is one of hosts!
# Also note that jar SHOULD be real executable (on unix anyway)

CONFIG="sample/single-node-9090.yml"
./ts-commands/target/transistore-commands-0.9.8-SNAPSHOT.jar \
 --config-file $CONFIG $@
