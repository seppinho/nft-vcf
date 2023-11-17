package genepi.nf.test.vcf;

import java.util.Set;

public class VcfFile {

	private Set<String> chromosomes;

	private String vcfFilename;

	private int noSamples = -1;

	private int noSnps = -1;

	private boolean phased = true;

	private boolean phasedAutodetect = true;

	private boolean chrPrefix;

	public int getNoSnps() {
		return noSnps;
	}

	public void setNoSnps(int noSnps) {
		this.noSnps = noSnps;
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

	public int getNoSamples() {
		return noSamples;
	}

	public void setVcfFilename(String vcfFilename) {
		this.vcfFilename = vcfFilename;
	}

	public void setNoSamples(int noSamples) {
		this.noSamples = noSamples;
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
	
	public String toString() {
		return "Chromosome: " + getChromosome() + "\n Samples: " + getNoSamples() + "\n Snps: " + getNoSnps();
	}

}
