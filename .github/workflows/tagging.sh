#!/usr/bin/env bash
getVer(){
  IFS='=' read -ra ARR <<< "$(grep "$1" gradle.properties)"
  read -r -d '' "$1" <<< "${ARR[1]}"
  unset IFS
  unset ARR
}
getVer MC_VERSION
echo "Got MC_VERSION: $MC_VERSION"
getVer FORGE_VERSION
echo "Got FORGE_VERSION: $FORGE_VERSION"
getVer KETTING_VERSION
echo "Got KETTING_VERSION: $KETTING_VERSION"
tag=v$MC_VERSION-$FORGE_VERSION-$KETTING_VERSION
echo "Got tag $tag"
echo "tag=$tag" >> "$GITHUB_OUTPUT"
echo "Set tag as output"
git tag "$tag"
echo "Created local tag"
git push origin "$tag"
echo "Pushed to remote"