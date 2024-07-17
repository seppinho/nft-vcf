package genepi.nf.test.vcf;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Set;

import genepi.io.text.LineReader;
import htsjdk.samtools.SAMSequenceDictionary;
import htsjdk.samtools.util.CloseableIterator;
import htsjdk.tribble.index.IndexFactory;
import htsjdk.tribble.index.tabix.TabixFormat;
import htsjdk.tribble.index.tabix.TabixIndex;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.VCFCodec;
import htsjdk.variant.vcf.VCFFileReader;
import htsjdk.variant.vcf.VCFHeader;

public class VcfFile {

	private Set<String> chromosomes;

	private String vcfFilename;

	private VCFHeader header;

	private int sampleCount = -1;

	private int variantCount = -1;

	private boolean phased = true;

	private boolean phasedAutodetect = true;

	public int getVariantCount() {
		return variantCount;
	}

	public void setVariantCount(int snps) {
		this.variantCount = snps;
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
		return "VcfFile [chromosomes=" + chromosomes + ", sampleCount=" + sampleCount + ", variantCount=" + variantCount + ", phased=" + phased
				+ ", phasedAutodetect=" + phasedAutodetect + "]";
	}
	
	public VariantContext getVariant(String chromosome, int position) throws IOException {
		createIndex();
		VCFFileReader reader = new VCFFileReader(new File(vcfFilename), true);
		CloseableIterator<VariantContext> it = reader.query(chromosome, position, position);
		
		while (it.hasNext()) {
			VariantContext line = it.next();
			reader.close();
			return line;
			}
		
		reader.close();
		return null;
	}
	
	public ArrayList<VariantContext> getVariants() throws IOException {
		return getVariants(-1);
	}

	public ArrayList<VariantContext> getVariants(int linesToReturn) throws IOException {
		VCFFileReader reader = new VCFFileReader(new File(vcfFilename), false);
		CloseableIterator<VariantContext> it = reader.iterator();
		ArrayList<VariantContext> variants = new ArrayList<VariantContext>();
		int count = 0;

		while (it.hasNext() && (count < linesToReturn || linesToReturn == -1)) {
			VariantContext line = it.next();
			count++;
			variants.add(line);
		}
		reader.close();
		return variants;
	}

	public ArrayList<String> getVariantsAsStrings() throws IOException {
		return getVariantsAsStrings(-1);
	}

	public ArrayList<String> getVariantsAsStrings(int linesToReturn) throws IOException {
		LineReader lineReader = new LineReader(vcfFilename.toString());
		ArrayList<String> variants = new ArrayList<String>();
		int count = 0;

		while (lineReader.next() && (count < linesToReturn || linesToReturn == -1)) {
			String line = lineReader.get();

			if (!line.startsWith("#")) {
				count++;
				variants.add(line);
			}
		}

		lineReader.close();
		return variants;
	}

	public String getVariantsMD5() throws IOException, NoSuchAlgorithmException {
		LineReader lineReader = new LineReader(vcfFilename.toString());
		MessageDigest md = MessageDigest.getInstance("MD5");

		while (lineReader.next()) {
			String line = lineReader.get();
			if (!line.startsWith("#")) {
				md.update(line.getBytes("UTF-8"));
			}

		}
		lineReader.close();
		return new BigInteger(1, md.digest()).toString(16);
	}
	
	public ArrayList<VariantContext> getVariantsByRange(String chromosome, int start, int stop) throws IOException {

		createIndex();
		VCFFileReader reader = new VCFFileReader(new File(vcfFilename), true);
		ArrayList<VariantContext> variants = new ArrayList<VariantContext>();
		CloseableIterator<VariantContext> it = reader.query(chromosome, start, stop);
		
		while (it.hasNext()) {
			VariantContext line = it.next();
			variants.add(line);
			}
		
		reader.close();
		return variants;

	}	
	
	public double getInfoR2(String chromosome, int position) throws IOException {
		String value = getInfoTag("R2", chromosome, position);
		return Double.valueOf(value);
	}
	
	public String getInfoTag(String tag, String chromosome, int position) throws IOException {
		VariantContext vc = getVariant(chromosome, position);
		return vc.getAttributeAsString(tag, "N/A");
	}	

	@Deprecated
	public int getNoSnps() {
		return variantCount;
	}

	@Deprecated
	public void setNoSnps(int noSnps) {
		this.variantCount = noSnps;
	}

	@Deprecated
	public int getNoSamples() {
		return sampleCount;
	}

	@Deprecated
	public void setNoSamples(int noSamples) {
		this.sampleCount = noSamples;
	}

	public void createIndex() throws IOException {
        File vcfFile = new File(vcfFilename);
        VCFFileReader reader = new VCFFileReader(vcfFile, false);
        SAMSequenceDictionary vcfDict = reader.getFileHeader().getSequenceDictionary();
		TabixIndex index = IndexFactory.createTabixIndex(new File(vcfFilename), new VCFCodec(), TabixFormat.VCF, vcfDict);
		index.writeBasedOnFeatureFile(new File(vcfFilename));
		reader.close();
	}

}
