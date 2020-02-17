package com.supv.challenge.web.rest;

import static com.supv.challenge.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import com.supv.challenge.ChallengeApplication;
import com.supv.challenge.domain.Relation;
import com.supv.challenge.repository.RelationRepository;
import com.supv.challenge.service.RelationService;
import com.supv.challenge.service.dto.RelationDTO;
import com.supv.challenge.service.mapper.RelationMapper;
import com.supv.challenge.web.rest.RelationResource;
import com.supv.challenge.web.rest.errors.ExceptionTranslator;

/**
 * Integration tests for the {@link RelationResource} REST controller.
 */
@SpringBootTest(classes = ChallengeApplication.class)
public class RelationResourceIT {

    private static final Integer DEFAULT_PERSON_ID = 1;
    private static final Integer UPDATED_PERSON_ID = 2;

    private static final Integer DEFAULT_FATHER_ID = 1;
    private static final Integer UPDATED_FATHER_ID = 2;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private RelationService relationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRelationMockMvc;

    private Relation relation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RelationResource relationResource = new RelationResource(relationService);
        this.restRelationMockMvc = MockMvcBuilders.standaloneSetup(relationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relation createEntity(EntityManager em) {
        Relation relation = new Relation()
            .personId(DEFAULT_PERSON_ID)
            .fatherId(DEFAULT_FATHER_ID);
        return relation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relation createUpdatedEntity(EntityManager em) {
        Relation relation = new Relation()
            .personId(UPDATED_PERSON_ID)
            .fatherId(UPDATED_FATHER_ID);
        return relation;
    }

    @BeforeEach
    public void initTest() {
        relation = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelation() throws Exception {
        int databaseSizeBeforeCreate = relationRepository.findAll().size();

        // Create the Relation
        RelationDTO relationDTO = relationMapper.toDto(relation);
        restRelationMockMvc.perform(post("/api/relations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationDTO)))
            .andExpect(status().isCreated());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeCreate + 1);
        Relation testRelation = relationList.get(relationList.size() - 1);
        assertThat(testRelation.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testRelation.getFatherId()).isEqualTo(DEFAULT_FATHER_ID);
    }

    @Test
    @Transactional
    public void createRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relationRepository.findAll().size();

        // Create the Relation with an existing ID
        relation.setId(1L);
        RelationDTO relationDTO = relationMapper.toDto(relation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationMockMvc.perform(post("/api/relations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPersonIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationRepository.findAll().size();
        // set the field null
        relation.setPersonId(null);

        // Create the Relation, which fails.
        RelationDTO relationDTO = relationMapper.toDto(relation);

        restRelationMockMvc.perform(post("/api/relations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationDTO)))
            .andExpect(status().isBadRequest());

        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelations() throws Exception {
        // Initialize the database
        relationRepository.saveAndFlush(relation);

        // Get all the relationList
        restRelationMockMvc.perform(get("/api/relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relation.getId().intValue())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID)))
            .andExpect(jsonPath("$.[*].fatherId").value(hasItem(DEFAULT_FATHER_ID)));
    }
    
    @Test
    @Transactional
    public void getRelation() throws Exception {
        // Initialize the database
        relationRepository.saveAndFlush(relation);

        // Get the relation
        restRelationMockMvc.perform(get("/api/relations/{id}", relation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relation.getId().intValue()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID))
            .andExpect(jsonPath("$.fatherId").value(DEFAULT_FATHER_ID));
    }

    @Test
    @Transactional
    public void getNonExistingRelation() throws Exception {
        // Get the relation
        restRelationMockMvc.perform(get("/api/relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelation() throws Exception {
        // Initialize the database
        relationRepository.saveAndFlush(relation);

        int databaseSizeBeforeUpdate = relationRepository.findAll().size();

        // Update the relation
        Relation updatedRelation = relationRepository.findById(relation.getId()).get();
        // Disconnect from session so that the updates on updatedRelation are not directly saved in db
        em.detach(updatedRelation);
        updatedRelation
            .personId(UPDATED_PERSON_ID)
            .fatherId(UPDATED_FATHER_ID);
        RelationDTO relationDTO = relationMapper.toDto(updatedRelation);

        restRelationMockMvc.perform(put("/api/relations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationDTO)))
            .andExpect(status().isOk());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
        Relation testRelation = relationList.get(relationList.size() - 1);
        assertThat(testRelation.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testRelation.getFatherId()).isEqualTo(UPDATED_FATHER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingRelation() throws Exception {
        int databaseSizeBeforeUpdate = relationRepository.findAll().size();

        // Create the Relation
        RelationDTO relationDTO = relationMapper.toDto(relation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelationMockMvc.perform(put("/api/relations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRelation() throws Exception {
        // Initialize the database
        relationRepository.saveAndFlush(relation);

        int databaseSizeBeforeDelete = relationRepository.findAll().size();

        // Delete the relation
        restRelationMockMvc.perform(delete("/api/relations/{id}", relation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
