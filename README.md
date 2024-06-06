# nft-vcf

nf-test plugin to provide support for VCF files.

## Requirements

- nf-test version 0.7.0 or higher

## Setup

To use this plugin you need to activate the `nft-vcf` plugin in your `nf-test.config` file:

```
config {
  plugins {
    load "nft-vcf@1.0.2"
  }
}
```

## Usage

nft-vcf extends `path` by a `vcf` property that can be used to read VCF files.


## Examples

```groovy
def vcfFile = path("${outputDir}/chr20.dose.vcf.gz").vcf
assert vcfFile.getChromosome() == "20"
assert vcfFile.getNoSamples() == 51
assert vcfFile.isPhased()
assert vcfFile.getNoSnps() == 1020
```
