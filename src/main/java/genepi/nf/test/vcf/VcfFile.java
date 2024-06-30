package genepi.nf.test.vcf;

import java.util.Set;

import htsjdk.variant.vcf.VCFHeader;

public class VcfFile {

	private Set<String> chromosomes;

	private String vcfFilename;

	private VCFHeader header;

	private int sampleCount = -1;

	private int snpCount = -1;

	private boolean phased = true;

	private boolean phasedAutodetect = true;

	private boolean chrPrefix;

	public int getSnpCount() {
		return snpCount;
	}

	public void setSnpCount(int snps) {
		this.snpCount = snps;
	}

	public Set<String> getChromosomes() {
		return chromosomes;
	}

	public String getChromosome() {
		return chromosomes.iterator().next();
	}

	public String getVcfFilename() {
		return vcfFilename;
	}

	public void setVcfFilename(String vcfFilename) {
		this.vcfFilename = vcfFilename;
	}

	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int noSamples) {
		this.sampleCount = noSamples;
	}

	public void setChromosomes(Set<String> chromosomes) {
		this.chromosomes = chromosomes;
	}

	public void setChrPrefix(boolean chrPrefix) {
		this.chrPrefix = chrPrefix;
	}

	public boolean hasChrPrefix() {
		return this.chrPrefix;
	}

	public void setPhased(boolean phased) {
		this.phased = phased;
	}

	public boolean isPhased() {
		return phased;
	}

	public String getType() {

		if (phased) {
			return "VCF-PHASED";
		} else {
			return "VCF-UNPHASED";
		}
	}

	public boolean isPhasedAutodetect() {
		return phasedAutodetect;
	}

	public void setPhasedAutodetect(boolean phasedAutodetect) {
		this.phasedAutodetect = phasedAutodetect;
	}

	public VCFHeader getHeader() {
		return header;
	}

	public void setHeader(VCFHeader header) {
		this.header = header;
	}
	
	public String getSummary() {
		return this.toString();
	}	

	@Override
	public String toString() {
		return "VcfFile [chromosomes=" + chromosomes + ", noSamples=" + sampleCount + ", noSnps=" + snpCount
				+ ", phased=" + phased + ", phasedAutodetect=" + phasedAutodetect + ", chrPrefix=" + chrPrefix + "]";
	}

	@Deprecated
	public int getNoSnps() {
		return snpCount;
	}

	@Deprecated
	public void setNoSnps(int noSnps) {
		this.snpCount = noSnps;
	}
	
	@Deprecated
	public int getNoSamples() {
		return sampleCount;
	}
	
	@Deprecated
	public void setNoSamples(int noSamples) {
		this.sampleCount = noSamples;
	}	

}
