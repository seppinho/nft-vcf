# nft-vcf

nf-test plugin to support for VCF files.

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
Returns the [VCF Header](https://samtools.github.io/htsjdk/javadoc/htsjdk/htsjdk/variant/vcf/VCFHeader.html) instance and allows you to access all available methods.
```groovy
assert path("file.vcf.gz").vcf.header.getColumnCount() == 4
```

### `summary`
Returns VCF summary attributes (chromosomes, variantCount, sampleCount, phasing status).
```groovy
path("file.vcf.gz").vcf.summary
```
### `getVariant(String chromosome, String position)`
Returns a [VariantContext](https://samtools.github.io/htsjdk/javadoc/htsjdk/htsjdk/variant/variantcontext/VariantContext.html) instance and allows you to access all available methods.
```groovy
path("file.vcf.gz").vcf.getVariant("chr20",123)
path("file.vcf.gz").vcf.getVariant("chr20",123).getContig()
path("file.vcf.gz").vcf.getVariant("chr20",123).getHetCount()
path("file.vcf.gz").vcf.getVariant("chr20",123).getAttribute("XX")
```
### `variants`
Returns an array of [VariantContext](https://samtools.github.io/htsjdk/javadoc/htsjdk/htsjdk/variant/variantcontext/VariantContext.html) instances and allows you to access all available methods.
```groovy
path("file.vcf.gz").vcf.variants.size()
```

### `getVariants(int numberOflines)`
Returns an array of [VariantContext](https://samtools.github.io/htsjdk/javadoc/htsjdk/htsjdk/variant/variantcontext/VariantContext.html) instances and allows you to access all available methods.
```groovy
path("file.vcf.gz").vcf.getVariants(100).size()
```

### `variantsMD5`
Returns the MD5 hashsum of all variants.
```groovy
path("file.vcf.gz").vcf.variantsMD5
```

### `getVariantsByRange(String chromosome, int start, int stop)`
Returns an array of [VariantContext](https://samtools.github.io/htsjdk/javadoc/htsjdk/htsjdk/variant/variantcontext/VariantContext.html) instances and allows you to access all available methods.
```groovy
path("file.vcf.gz").vcf.getVariantsRange("chr20", 1, 10)
```

### `getInfoR2(String chromosome, int position)`
Returns a the INFO R2 value of a specific variant.
```groovy
path("file.vcf.gz").vcf.getInfoR2("chr20", 1)
```

### `getInfoTag(String tag, String chromosome, int position)`
Returns the specified INFO field value of a particular variant.
```groovy
path("file.vcf.gz").vcf.getInfoTag("R2", "chr20", 1)
```

### `createIndex()`
Create a tabix index for the specified VCF file.
```groovy
path("file.vcf.gz").vcf.createIndex()
```
