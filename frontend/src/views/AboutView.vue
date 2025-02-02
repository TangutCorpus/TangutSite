<template>
  <div class="container mx-auto p-6">
    <SectionWrapper id="features" title="Features">
      <FeatureList :features="features" />
    </SectionWrapper>

    <SectionWrapper id="team" title="Our Team">
      <TeamList :members="team" />
    </SectionWrapper>

    <SectionWrapper id="pricing" title="Pricing">
      <PricingTable :headers="pricingHeaders" :rows="pricingRows" />
    </SectionWrapper>

    <SectionWrapper id="contact" title="Contact">
      <ContactForm />
    </SectionWrapper>

    <SectionWrapper id="faq" title="FAQ">
      <FAQList :faqs="faqs" />
    </SectionWrapper>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import FeatureList from '@/components/FeatureList.vue';
import TeamList from '@/components/TeamList.vue';
import PricingTable from '@/components/PricingTable.vue';
import ContactForm from '@/components/ContactForm.vue';
import FAQList from '@/components/FAQList.vue';
import SectionWrapper from '@/components/SectionWrapper.vue';
import Image from '@/assets/404.svg'

const features = ref([{ title: "Lorem Ipsum", description: "Lorem Ipsum" }]);
const team = ref([
  { name: "Lorem Ipsum", role: "Lorem Ipsum", image: Image },
  { name: "Lorem Ipsum", role: "Lorem Ipsum", image: Image },
  { name: "Lorem Ipsum", role: "Lorem Ipsum", image: Image }]
);
const pricingHeaders = ref(["Lorem Ipsum"]);
const pricingRows = ref([{ id: 1, values: ["Lorem Ipsum"] }]);
const faqs = ref([{ question: "Lorem Ipsum?", answer: "Lorem Ipsum" }]);
</script>