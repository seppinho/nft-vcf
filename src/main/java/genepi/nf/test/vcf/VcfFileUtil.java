package genepi.nf.test.vcf;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import genepi.io.text.LineReader;
import htsjdk.variant.vcf.VCFFileReader;
import htsjdk.variant.vcf.VCFHeader;

public class VcfFileUtil {

	public static VcfFile load(Path vcfFilename) throws IOException {

		Set<String> chromosomes = new HashSet<String>();
		Set<String> rawChromosomes = new HashSet<String>();
		int snps = 0;
		int samples = 0;

		try {

			VCFFileReader reader = new VCFFileReader(vcfFilename, false);

			samples = reader.getFileHeader().getGenotypeSamples().size();

			VCFHeader header = reader.getFileHeader();

			reader.close();

			LineReader lineReader = new LineReader(vcfFilename.toString());

			boolean phased = true;
			boolean phasedAutodetect = true;
			boolean firstLine = true;
			while (lineReader.next()) {

				String line = lineReader.get();

				if (!line.startsWith("#")) {

					String tiles[] = line.split("\t", 10);

					if (tiles.length < 3) {
						throw new IOException("The provided VCF file is not tab-delimited");
					}

					String chromosome = tiles[0];
					rawChromosomes.add(chromosome);
					chromosome = chromosome.replaceAll("chr", "");

					if (samples > 0) {

						if (phased) {
							boolean containsSymbol = tiles[9].contains("/");

							if (containsSymbol) {
								phased = false;
							}

						}

						if (firstLine) {
							boolean containsSymbol = tiles[9].contains("/") || tiles[9].contains(".");

							if (!containsSymbol) {
								phasedAutodetect = true;
							} else {
								phasedAutodetect = false;
							}
							firstLine = false;

						}
					}

					chromosomes.add(chromosome);
					if (chromosomes.size() > 1) {
						throw new IOException(
								"The provided VCF file contains more than one chromosome. Please split your input VCF file by chromosome");
					}

					String ref = tiles[3];
					String alt = tiles[4];

					if (ref.equals(alt)) {
						throw new IOException("The provided VCF file is malformed at variation " + tiles[2]
								+ ": reference allele (" + ref + ") and alternate allele  (" + alt + ") are the same.");
					}

					snps++;

				} else {

					if (line.startsWith("#CHROM")) {

						String[] tiles = line.split("\t");

						// check sample names, stop when not unique
						HashSet<String> uniqueSamples = new HashSet<>();

						for (int i = 0; i < tiles.length; i++) {

							String sample = tiles[i];

							if (uniqueSamples.contains(sample)) {
								reader.close();
								throw new IOException("Two individuals or more have the following ID: " + sample);
							}
							uniqueSamples.add(sample);
						}
					}

				}

			}
			lineReader.close();

			VcfFile file = new VcfFile();
			file.setVcfFilename(vcfFilename.toString());
			file.setSnpCount(snps);
			file.setSampleCount(samples);
			file.setChromosomes(chromosomes);
			file.setHeader(header);

			boolean hasChrPrefix = false;
			for (String chromosome : rawChromosomes) {
				if (chromosome.startsWith("chr")) {
					hasChrPrefix = true;
				}
			}
			file.setChrPrefix(hasChrPrefix);
			file.setPhased(phased);
			file.setPhasedAutodetect(phasedAutodetect);
			return file;

		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}

	}

}