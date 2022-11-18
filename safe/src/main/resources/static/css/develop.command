#!/bin/sh

cd $(dirname $0)
sass --style compressed --watch . --no-cache
