# nft-vcf

nf-test plugin to support VCF files.

## Requirements

- nf-test version 0.7.0 or higher

## Setup

To use this plugin you need to activate the `nft-vcf` plugin in your `nf-test.config` file:

```
config {
  plugins {
    load "nft-vcf@1.0.5"
  }
}
```

## Usage

nft-vcf extends `path` by a `vcf` property that can be used to read VCF files. It returns VCF lines in String format and makes extensive use of [HTSJDK](https://github.com/samtools/htsjdk).


## Examples

```groovy
def vcfFile = path("${outputDir}/chr20.dose.vcf.gz").vcf
assert vcfFile.chromosome == "20" 
assert vcfFile.sampleCount == 51
assert vcfFile.phased
assert vcfFile.variantCount == 7824

//or
with(path(filename).vcf) {
   assert chromosome == "20"
   assert sampleCount == 51
   assert phased
   assert variantCount == 7824     
}
```

### `header`
Header returns a htsjdk.variant.vcf.VCFHeader object. All VCFHeader methods can be accessed.
```groovy
assert path("file.vcf.gz").vcf.header.getColumnCount() == 4
```

### `summary`
Returns a string containing specific VCF summary attributes (chromosomes, variantCount, sampleCount, phasing status).
```groovy
path("file.vcf.gz").vcf.summary
```
### `getVariant`
Returns a variantContext object containing a variant defined by chromosome and position. All variantContext methods can be accessed.
```groovy
path("file.vcf.gz").vcf.getVariant("chr20",123)
path("file.vcf.gz").vcf.getVariant("chr20",123).getContig()
path("file.vcf.gz").vcf.getVariant("chr20",123).getHetCount()
path("file.vcf.gz").vcf.getVariant("chr20",123).getAttribute("XX")
```

### `getVariants`
Returns a string array containing all variants or the first n variants. 
```groovy
path("file.vcf.gz").vcf.variants.size()
path("file.vcf.gz").vcf.getVariants(100).size()
```

### `getVariantsMD5`
Returns a string value containing the MD5 hashsum of all variants.
```groovy
path("file.vcf.gz").vcf.getVariantsMD5()
```

### `getVariantsRange`
Returns a string array containing a range of variants.
```groovy
path("file.vcf.gz").vcf.getVariantsRange("chr20", 1, 10)
```

### `getInfoR2`
Returns a double value containing the INFO R2 value of a specific variant.
```groovy
path("file.vcf.gz").vcf.getInfoR2("chr20", 1, 10)
```

### `getInfoTag`
Returns a string value containing the specified INFO field of a particular variant.
```groovy
path("file.vcf.gz").vcf.getInfoTag("R2", "chr20", 1, 10)
```

### `createIndex`
Create a TABIX index for the vcf file.
```groovy
path("file.vcf.gz").vcf.createIndex()
```
