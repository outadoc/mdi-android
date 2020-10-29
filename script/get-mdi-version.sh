#!/usr/bin/env bash

npm list @mdi/font --json | jq -r .dependencies[].version
