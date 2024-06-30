process TEST_VCF {

  input:
    path input_vcf

  output:
     path "output_*", emit: vcf


  """
	cp ${input_vcf} output_${input_vcf.name}
  """

}
