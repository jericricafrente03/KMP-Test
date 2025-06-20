package com.jeric.ricafrente.naruto.composable_test

import com.jeric.ricafrente.naruto.composable_test.repository.FakeRepositoryImpl
import com.jeric.ricafrente.naruto.composable_test.utils.akatsukiList
import com.jeric.ricafrente.naruto.composable_test.utils.characterList
import com.jeric.ricafrente.naruto.composable_test.utils.getTailedBeast
import com.jeric.ricafrente.naruto.composable_test.utils.karaList
import com.jeric.ricafrente.naruto.core.use_cases.UseCases
import com.jeric.ricafrente.naruto.core.use_cases.get_all_akatsuki.GetAllAkatsukiUseCase
import com.jeric.ricafrente.naruto.core.use_cases.get_all_akatsuki.GetSelectedAkatsukiUseCase
import com.jeric.ricafrente.naruto.core.use_cases.get_all_character.GetAllCharacterUseCase
import com.jeric.ricafrente.naruto.core.use_cases.get_all_clan.GetAllClanUseCase
import com.jeric.ricafrente.naruto.core.use_cases.get_all_kara.GetAllKaraUseCase
import com.jeric.ricafrente.naruto.core.use_cases.get_all_kara.GetSelectedKaraUseCase
import com.jeric.ricafrente.naruto.core.use_cases.get_all_tailbeast.GetAllTailBeastUseCase
import com.jeric.ricafrente.naruto.core.use_cases.get_all_tailbeast.GetSelectedTailedBeastUseCase
import com.jeric.ricafrente.naruto.core.use_cases.search_character.GetSelectedHeroesUseCase
import com.jeric.ricafrente.naruto.core.use_cases.search_character.SearchHeroesUseCase
import com.jeric.ricafrente.naruto.data.repository.RemoteDataSourceImplTest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeUnitTesting {
    private lateinit var getAllAkatsukiUseCase: GetAllAkatsukiUseCase
    private lateinit var getSelectedAkatsukiUseCase: GetSelectedAkatsukiUseCase
    private lateinit var getAllClanUseCase: GetAllClanUseCase
    private lateinit var getAllCharacterUseCase: GetAllCharacterUseCase
    private lateinit var getAllKaraUseCase: GetAllKaraUseCase
    private lateinit var getSelectedKaraUseCase: GetSelectedKaraUseCase
    private lateinit var getAllTailBeastUseCase: GetAllTailBeastUseCase
    private lateinit var getSelectedTailedBeastUseCase: GetSelectedTailedBeastUseCase
    private lateinit var getSearchHeroesUseCase: SearchHeroesUseCase
    private lateinit var getSelectedHeroesUseCase: GetSelectedHeroesUseCase

    private lateinit var getAllUseCases: UseCases

    private lateinit var remoteDataSource: FakeRepositoryImpl
    private lateinit var fakeSuccessRepo: RemoteDataSourceImplTest

    @BeforeTest
    fun setUp() {
        remoteDataSource = FakeRepositoryImpl()
        fakeSuccessRepo = RemoteDataSourceImplTest(
            remote = remoteDataSource
        )
        getAllKaraUseCase = GetAllKaraUseCase(repository = fakeSuccessRepo)
        getAllClanUseCase = GetAllClanUseCase(repository = fakeSuccessRepo)
        getAllAkatsukiUseCase = GetAllAkatsukiUseCase(repository = fakeSuccessRepo)
        getSelectedKaraUseCase = GetSelectedKaraUseCase(repository = fakeSuccessRepo)
        getAllTailBeastUseCase = GetAllTailBeastUseCase(repository = fakeSuccessRepo)
        getAllCharacterUseCase = GetAllCharacterUseCase(repository = fakeSuccessRepo)
        getSelectedAkatsukiUseCase = GetSelectedAkatsukiUseCase(repository = fakeSuccessRepo)
        getSearchHeroesUseCase = SearchHeroesUseCase(repository = fakeSuccessRepo)
        getSelectedHeroesUseCase = GetSelectedHeroesUseCase(repository = fakeSuccessRepo)
        getSelectedTailedBeastUseCase = GetSelectedTailedBeastUseCase(repository = fakeSuccessRepo)


        getAllUseCases = UseCases(
            getAllKaraUseCase = getAllKaraUseCase,
            getAllClanUseCase = getAllClanUseCase,
            getAllAkatsukiUseCase = getAllAkatsukiUseCase,
            getSelectedKaraUseCase = getSelectedKaraUseCase,
            getAllTailBeastUseCase = getAllTailBeastUseCase,
            getAllCharacterUseCase = getAllCharacterUseCase,
            getSelectedAkatsukiUseCase = getSelectedAkatsukiUseCase,
            searchHeroesUseCase = getSearchHeroesUseCase,
            getSelectedHeroesUseCase = getSelectedHeroesUseCase,
            getSelectedTailedBeastUseCase = getSelectedTailedBeastUseCase
        )
    }

    private val testId = 1

    @Test
    fun shouldReturnCorrectTailedBeast_whenIdProvided() = runTest {
        val expected = getTailedBeast().find { it.id == testId }
        val result = getSelectedTailedBeastUseCase(testId)
        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnCorrectKara_whenIdProvided() = runTest {
        val expected =  karaList().find { it.id == testId.toString() }
        val result = getSelectedKaraUseCase(testId)
        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnCorrectAkatsuki_whenIdProvided() = runTest {
        val expected = akatsukiList().find { it.id == testId.toString() }
        val result = getSelectedAkatsukiUseCase(testId)
        assertEquals(expected, result)
    }

    @Test
    fun getTheExactSizeOfTailedBeast_whenProvided() = runTest {
        val result = getAllTailBeastUseCase().last().data?.size
        val expectedSize = getTailedBeast().size
        assertEquals(expectedSize, result)
    }

    @Test
    fun getTheExactSizeOfKara_whenProvided() = runTest {
        val result = getAllKaraUseCase().last().data?.size
        val expectedSize = getTailedBeast().size
        assertEquals(expectedSize, result)
    }

    @Test
    fun getTheExactSizeOfAkatsuki_whenProvided() = runTest {
        val result = getAllAkatsukiUseCase().last().data?.size
        val expectedSize = getTailedBeast().size
        assertEquals(expectedSize, result)
    }

    @Test
    fun getTheExactSizeOfClan_whenProvided() = runTest {
        val result = getAllClanUseCase().last().data?.size
        val expectedSize = getTailedBeast().size
        assertEquals(expectedSize, result)
    }

}